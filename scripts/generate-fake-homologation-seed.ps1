$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $PSScriptRoot
$sqlFiles = @(
    (Join-Path $projectRoot "backup - locadora.sql"),
    (Join-Path $projectRoot "src/main/resources/db/init.sql")
)

function Escape-Sql {
    param([string]$Text)
    return $Text.Replace("'", "''")
}

function Replace-Block {
    param(
        [string]$Content,
        [string]$TableName,
        [string]$NextTableName,
        [string]$NewBlock
    )

    $pattern = "(?s)-- Copiando dados para a tabela dtb-locadora-rich\.${TableName}:.*?(?=\r?\n-- Copiando estrutura para tabela dtb-locadora-rich\.${NextTableName})"
    return [regex]::Replace($Content, $pattern, $NewBlock, 1)
}

$clienteBlock = @'
-- Copiando dados para a tabela dtb-locadora-rich.cliente: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`id_cliente`, `nome`, `cpf`, `rg`, `data_nascimento`, `idade`, `sexo`, `endereco_id_endereco`, `contato_id_contato`) VALUES
	(1, 'Cliente Teste Alfa', '111.222.333-44', '12.345.678-9', '10/02/1995', 30, 'Feminino', 4, 4),
	(2, 'Cliente Teste Beta', '555.666.777-88', '98.765.432-1', '25/08/1991', 33, 'Masculino', 5, 5);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
'@

$funcionarioBlock = @'
-- Copiando dados para a tabela dtb-locadora-rich.funcionario: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
INSERT INTO `funcionario` (`id_funcionario`, `nome`, `cpf`, `rg`, `data_nascimento`, `idade`, `sexo`, `login`, `senha`, `perfil_acesso`, `contato_id_contato`, `endereco_id_endereco`) VALUES
	(1, 'Operador Teste Admin', '123.123.123-12', '12.123.123-1', '10/02/1980', 44, 'Masculino', 'admin', 'admin', 'Administrador', 1, 1),
	(2, 'Operador Teste Dev', '456.456.456-45', '75.378.657-5', '05/05/1996', 28, 'Masculino', 'dev', 'dev', 'Desenvolvedor', 2, 2),
	(3, 'Operador Teste User', '987.987.987-98', '65.465.465-4', '01/03/1993', 31, 'Masculino', 'user', 'user', 'Usuario', 3, 3);
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
'@

$rentalGameMap = @(
    @{ LocacaoId = 1; Jogos = @(4, 8); Devolvido = "Sim"; DataLocacao = "12/01/2024"; DataDevolucao = "19/01/2024"; Cliente = 1; Forma = 1; Func = 2 },
    @{ LocacaoId = 2; Jogos = @(16, 20); Devolvido = "Sim"; DataLocacao = "08/02/2024"; DataDevolucao = "15/02/2024"; Cliente = 2; Forma = 6; Func = 3 },
    @{ LocacaoId = 3; Jogos = @(52, 64); Devolvido = "Sim"; DataLocacao = "22/03/2024"; DataDevolucao = "29/03/2024"; Cliente = 1; Forma = 2; Func = 1 },
    @{ LocacaoId = 4; Jogos = @(88, 92); Devolvido = "Sim"; DataLocacao = "10/05/2024"; DataDevolucao = "17/05/2024"; Cliente = 2; Forma = 3; Func = 2 },
    @{ LocacaoId = 5; Jogos = @(120, 124); Devolvido = "Sim"; DataLocacao = "14/07/2024"; DataDevolucao = "21/07/2024"; Cliente = 1; Forma = 4; Func = 3 },
    @{ LocacaoId = 6; Jogos = @(148, 152); Devolvido = "Sim"; DataLocacao = "03/09/2024"; DataDevolucao = "10/09/2024"; Cliente = 2; Forma = 5; Func = 1 },
    @{ LocacaoId = 7; Jogos = @(180, 188); Devolvido = "Sim"; DataLocacao = "18/11/2024"; DataDevolucao = "25/11/2024"; Cliente = 1; Forma = 1; Func = 2 },
    @{ LocacaoId = 8; Jogos = @(204, 208); Devolvido = "Sim"; DataLocacao = "07/12/2024"; DataDevolucao = "14/12/2024"; Cliente = 2; Forma = 6; Func = 3 },
    @{ LocacaoId = 9; Jogos = @(252, 256); Devolvido = "Sim"; DataLocacao = "15/01/2025"; DataDevolucao = "22/01/2025"; Cliente = 1; Forma = 2; Func = 1 },
    @{ LocacaoId = 10; Jogos = @(304, 312); Devolvido = "Sim"; DataLocacao = "09/03/2025"; DataDevolucao = "16/03/2025"; Cliente = 2; Forma = 3; Func = 2 },
    @{ LocacaoId = 11; Jogos = @(356, 364); Devolvido = "Sim"; DataLocacao = "24/04/2025"; DataDevolucao = "01/05/2025"; Cliente = 1; Forma = 4; Func = 3 },
    @{ LocacaoId = 12; Jogos = @(408, 416); Devolvido = "Sim"; DataLocacao = "13/06/2025"; DataDevolucao = "20/06/2025"; Cliente = 2; Forma = 5; Func = 1 },
    @{ LocacaoId = 13; Jogos = @(452, 460); Devolvido = "Sim"; DataLocacao = "02/08/2025"; DataDevolucao = "09/08/2025"; Cliente = 1; Forma = 1; Func = 2 },
    @{ LocacaoId = 14; Jogos = @(508, 520); Devolvido = "Sim"; DataLocacao = "19/10/2025"; DataDevolucao = "26/10/2025"; Cliente = 2; Forma = 6; Func = 3 },
    @{ LocacaoId = 15; Jogos = @(24, 28); Devolvido = "Sim"; DataLocacao = "05/12/2025"; DataDevolucao = "12/12/2025"; Cliente = 1; Forma = 2; Func = 1 },
    @{ LocacaoId = 16; Jogos = @(76, 84); Devolvido = "Nao"; DataLocacao = "10/01/2026"; DataDevolucao = "17/01/2026"; Cliente = 2; Forma = 3; Func = 2 },
    @{ LocacaoId = 17; Jogos = @(132, 148); Devolvido = "Nao"; DataLocacao = "11/02/2026"; DataDevolucao = "18/02/2026"; Cliente = 1; Forma = 4; Func = 3 },
    @{ LocacaoId = 18; Jogos = @(176, 180); Devolvido = "Nao"; DataLocacao = "09/03/2026"; DataDevolucao = "16/03/2026"; Cliente = 2; Forma = 5; Func = 1 }
)

$locacaoLines = New-Object System.Collections.Generic.List[string]
foreach ($rental in $rentalGameMap) {
    $separator = if ($rental.LocacaoId -lt $rentalGameMap.Count) { "," } else { ";" }
    $valorLocacao = [math]::Round(11.9 + ($rental.LocacaoId * 6.75), 2).ToString([System.Globalization.CultureInfo]::InvariantCulture)
    $locacaoLines.Add("	($($rental.LocacaoId), '$($rental.DataLocacao)', '$($rental.DataDevolucao)', $valorLocacao, '$($rental.Devolvido)', $($rental.Func), $($rental.Forma), $($rental.Cliente))$separator")
}

$locacaoBlockTemplate = @'
-- Copiando dados para a tabela dtb-locadora-rich.locacao: ~18 rows (aproximadamente)
/*!40000 ALTER TABLE `locacao` DISABLE KEYS */;
INSERT INTO `locacao` (`id_locacao`, `data_locacao`, `data_devolucao`, `valor`, `devolvido`, `funcionario_id_funcionario`, `forma_pagamento_id_forma_pagamento`, `cliente_id_cliente`) VALUES
__VALUES__
/*!40000 ALTER TABLE `locacao` ENABLE KEYS */;
'@
$locacaoBlock = $locacaoBlockTemplate.Replace("__VALUES__", ($locacaoLines -join "`r`n"))

$locacaoJogoLines = New-Object System.Collections.Generic.List[string]
$locacaoJogoId = 1
foreach ($rental in $rentalGameMap) {
    foreach ($gameId in $rental.Jogos) {
        $separator = if ($locacaoJogoId -lt ($rentalGameMap.Count * 2)) { "," } else { ";" }
        $locacaoJogoLines.Add("	($locacaoJogoId, $gameId, $($rental.LocacaoId))$separator")
        $locacaoJogoId++
    }
}

$locacaoJogoBlockTemplate = @'
-- Copiando dados para a tabela dtb-locadora-rich.locacao_jogo: ~36 rows (aproximadamente)
/*!40000 ALTER TABLE `locacao_jogo` DISABLE KEYS */;
INSERT INTO `locacao_jogo` (`id_locacao_jogo`, `jogo_id_jogo`, `locacao_id_locacao`) VALUES
__VALUES__
/*!40000 ALTER TABLE `locacao_jogo` ENABLE KEYS */;
'@
$locacaoJogoBlock = $locacaoJogoBlockTemplate.Replace("__VALUES__", ($locacaoJogoLines -join "`r`n"))

foreach ($sqlFile in $sqlFiles) {
    $content = Get-Content -Raw -Path $sqlFile
    $content = Replace-Block -Content $content -TableName "cliente" -NextTableName "contato" -NewBlock ($clienteBlock + "`r`n")
    $content = Replace-Block -Content $content -TableName "funcionario" -NextTableName "locacao" -NewBlock ($funcionarioBlock + "`r`n")
    $content = Replace-Block -Content $content -TableName "locacao" -NextTableName "locacao_jogo" -NewBlock ($locacaoBlock + "`r`n")
    $content = Replace-Block -Content $content -TableName "locacao_jogo" -NextTableName "regiao" -NewBlock ($locacaoJogoBlock + "`r`n")
    Set-Content -Path $sqlFile -Value $content -Encoding UTF8
}

Write-Host "Massa fake de homologacao gerada para clientes, funcionarios e locacoes, preservando o catalogo conhecido de jogos."
