$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $PSScriptRoot
$imagesRoot = Join-Path $projectRoot "src/main/webapp/resources/demo/images/jogos"
$sqlFiles = @(
    (Join-Path $projectRoot "backup - locadora.sql"),
    (Join-Path $projectRoot "src/main/resources/db/init.sql")
)

$schemaBlocks = [ordered]@{
    "cidade" = @'
CREATE TABLE IF NOT EXISTS `cidade` (
  `id_cidade` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(200) NOT NULL,
  `estado_id_estado` int NOT NULL,
  PRIMARY KEY (`id_cidade`),
  KEY `fk_cidade_estado1_idx` (`estado_id_estado`),
  CONSTRAINT `fk_cidade_estado1` FOREIGN KEY (`estado_id_estado`) REFERENCES `estado` (`id_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=5565 DEFAULT CHARSET=utf8;
'@
    "cliente" = @'
CREATE TABLE IF NOT EXISTS `cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cpf` varchar(45) NOT NULL,
  `rg` varchar(45) NOT NULL,
  `data_nascimento` varchar(45) NOT NULL,
  `idade` int NOT NULL,
  `sexo` varchar(45) NOT NULL,
  `endereco_id_endereco` int NOT NULL,
  `contato_id_contato` int NOT NULL,
  PRIMARY KEY (`id_cliente`),
  KEY `fk_cliente_endereco1_idx` (`endereco_id_endereco`),
  KEY `fk_cliente_contato1_idx` (`contato_id_contato`),
  CONSTRAINT `fk_cliente_contato1` FOREIGN KEY (`contato_id_contato`) REFERENCES `contato` (`id_contato`),
  CONSTRAINT `fk_cliente_endereco1` FOREIGN KEY (`endereco_id_endereco`) REFERENCES `endereco` (`id_endereco`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
'@
    "contato" = @'
CREATE TABLE IF NOT EXISTS `contato` (
  `id_contato` int NOT NULL AUTO_INCREMENT,
  `telefone` varchar(45) DEFAULT NULL,
  `celular` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_contato`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
'@
    "endereco" = @'
CREATE TABLE IF NOT EXISTS `endereco` (
  `id_endereco` int NOT NULL AUTO_INCREMENT,
  `tipo_logradouro` varchar(45) NOT NULL,
  `endereco` varchar(45) NOT NULL,
  `numero` int NOT NULL,
  `complemento` varchar(45) DEFAULT NULL,
  `bairro` varchar(45) NOT NULL,
  `cep` varchar(45) NOT NULL,
  `cidade_id_cidade` int NOT NULL,
  PRIMARY KEY (`id_endereco`),
  KEY `fk_endereco_cidade1_idx` (`cidade_id_cidade`),
  CONSTRAINT `fk_endereco_cidade1` FOREIGN KEY (`cidade_id_cidade`) REFERENCES `cidade` (`id_cidade`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
'@
    "estado" = @'
CREATE TABLE IF NOT EXISTS `estado` (
  `id_estado` int NOT NULL AUTO_INCREMENT,
  `codigo_uf` int NOT NULL,
  `nome` varchar(100) NOT NULL,
  `uf` varchar(45) NOT NULL,
  `regiao_id_regiao` int NOT NULL,
  PRIMARY KEY (`id_estado`),
  KEY `fk_estado_regiao1_idx` (`regiao_id_regiao`),
  CONSTRAINT `fk_estado_regiao1` FOREIGN KEY (`regiao_id_regiao`) REFERENCES `regiao` (`id_regiao`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
'@
    "jogo" = @'
CREATE TABLE IF NOT EXISTS `jogo` (
  `id_jogo` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL,
  `valor` double NOT NULL,
  `disponivel` varchar(45) NOT NULL,
  `promocao` varchar(45) NOT NULL,
  `valor_promocao` double DEFAULT NULL,
  `plataforma` varchar(45) NOT NULL,
  `ano_lancamento` varchar(45) NOT NULL,
  `faixa_etaria` int NOT NULL,
  `genero` varchar(45) NOT NULL,
  `imagem` varchar(255) NOT NULL,
  PRIMARY KEY (`id_jogo`)
) ENGINE=InnoDB AUTO_INCREMENT=551 DEFAULT CHARSET=utf8;
'@
}

$platformOrder = @(
    "PlayStation 5",
    "Xbox Series X|S",
    "Nintendo Switch",
    "PC",
    "Atari 2600",
    "Game Boy Color",
    "Game Boy Advance",
    "PlayStation 1",
    "PlayStation 2",
    "Super Nintendo",
    "NES 8 Bits"
)

$platformConfigs = @{
    "PlayStation 5" = @{
        Slug = "playstation-5"
        Primary = "#0b5cff"
        Secondary = "#59c6ff"
        Accent = "#f6fbff"
    }
    "Xbox Series X|S" = @{
        Slug = "xbox-series-xs"
        Primary = "#0f8f3a"
        Secondary = "#86d879"
        Accent = "#f3fff3"
    }
    "Nintendo Switch" = @{
        Slug = "nintendo-switch"
        Primary = "#d81f26"
        Secondary = "#ff8c69"
        Accent = "#fff6f2"
    }
    "PC" = @{
        Slug = "pc"
        Primary = "#202833"
        Secondary = "#6aa2ff"
        Accent = "#f5f8ff"
    }
    "Atari 2600" = @{
        Slug = "atari-2600"
        Primary = "#8f4f18"
        Secondary = "#f3a43b"
        Accent = "#fff7e7"
    }
    "Game Boy Color" = @{
        Slug = "game-boy-color"
        Primary = "#7b2cbf"
        Secondary = "#c77dff"
        Accent = "#fbf4ff"
    }
    "Game Boy Advance" = @{
        Slug = "game-boy-advance"
        Primary = "#4c5bdc"
        Secondary = "#8bc6ff"
        Accent = "#f4f8ff"
    }
    "PlayStation 1" = @{
        Slug = "playstation-1"
        Primary = "#5f6368"
        Secondary = "#b5bac1"
        Accent = "#ffffff"
    }
    "PlayStation 2" = @{
        Slug = "playstation-2"
        Primary = "#0b0f4d"
        Secondary = "#3a86ff"
        Accent = "#eef4ff"
    }
    "Super Nintendo" = @{
        Slug = "super-nintendo"
        Primary = "#6f2dbd"
        Secondary = "#ff4d6d"
        Accent = "#fff5fb"
    }
    "NES 8 Bits" = @{
        Slug = "nes-8-bits"
        Primary = "#4f4f4f"
        Secondary = "#d62828"
        Accent = "#fff6f6"
    }
}

$seedData = @{
    "PlayStation 5" = @'
Astro Bot|2024|Plataforma|10
Baldur's Gate 3|2023|RPG|16
Black Myth: Wukong|2024|RPG|16
Call of Duty: Black Ops 6|2024|FPS|18
Cyberpunk 2077|2020|RPG|18
Demon's Souls|2020|RPG|16
Death Stranding Director's Cut|2021|Aventura|16
Dragon's Dogma 2|2024|RPG|18
EA Sports FC 25|2024|Esporte|3
Elden Ring|2022|RPG|16
Final Fantasy VII Rebirth|2024|RPG|14
Ghost of Tsushima Director's Cut|2021|Acao|18
God of War Ragnarok|2022|Acao|18
Gran Turismo 7|2022|Corrida|3
Helldivers 2|2024|FPS|16
Hogwarts Legacy|2023|RPG|12
Horizon Forbidden West|2022|Mundo Aberto|16
Lies of P|2023|RPG|16
Marvel's Spider-Man 2|2023|Acao|12
Marvel's Spider-Man Remastered|2022|Acao|12
Mortal Kombat 1|2023|Luta|18
NBA 2K25|2024|Esporte|3
Nioh Collection|2021|RPG|18
No Man's Sky|2022|Survival|10
Persona 3 Reload|2024|RPG|14
Persona 5 Royal|2022|RPG|16
Prince of Persia: The Lost Crown|2024|Plataforma|12
Ratchet & Clank: Rift Apart|2021|Plataforma|10
Resident Evil 4|2023|Terror|18
Resident Evil Village|2021|Terror|18
Returnal|2021|FPS|16
Rise of the Ronin|2024|Acao|18
Sea of Stars|2023|RPG|10
Silent Hill 2|2024|Terror|18
Stellar Blade|2024|Acao|18
Street Fighter 6|2023|Luta|12
Tekken 8|2024|Luta|12
The Crew Motorfest|2023|Corrida|12
The Last of Us Part I|2022|Acao|18
The Last of Us Part II Remastered|2024|Acao|18
The Witcher 3: Wild Hunt|2022|RPG|18
Unicorn Overlord|2024|Estrategia|12
Alan Wake 2|2023|Terror|18
Assassin's Creed Mirage|2023|Acao|16
Like a Dragon: Infinite Wealth|2024|RPG|18
Metaphor: ReFantazio|2024|RPG|14
Diablo IV|2023|RPG|18
Star Wars Jedi: Survivor|2023|Aventura|12
WWE 2K24|2024|Esporte|12
Warhammer 40,000: Space Marine 2|2024|Acao|18
'@
    "Xbox Series X|S" = @'
Alan Wake 2|2023|Terror|18
Assassin's Creed Mirage|2023|Acao|16
Avowed|2025|RPG|16
Baldur's Gate 3|2023|RPG|16
Call of Duty: Black Ops 6|2024|FPS|18
Control Ultimate Edition|2021|Acao|16
Cyberpunk 2077|2020|RPG|18
Dead Space|2023|Terror|18
Diablo IV|2023|RPG|18
Doom Eternal|2021|FPS|18
EA Sports FC 25|2024|Esporte|3
Elden Ring|2022|RPG|16
Far Cry 6|2021|FPS|18
Forza Horizon 5|2021|Corrida|3
Forza Motorsport|2023|Corrida|3
Gears 5|2020|FPS|18
Grounded|2022|Survival|10
Hades|2021|RPG|12
Halo Infinite|2021|FPS|16
Hi-Fi Rush|2023|Acao|12
Hogwarts Legacy|2023|RPG|12
Indiana Jones and the Great Circle|2024|Aventura|14
Lies of P|2023|RPG|16
Like a Dragon: Infinite Wealth|2024|RPG|18
Mass Effect Legendary Edition|2021|RPG|18
Metaphor: ReFantazio|2024|RPG|14
Microsoft Flight Simulator|2021|Simulacao|3
Minecraft|2011|Sandbox|7
Monster Hunter Rise|2023|RPG|12
Mortal Kombat 1|2023|Luta|18
NBA 2K25|2024|Esporte|3
No Man's Sky|2020|Survival|10
Ori and the Will of the Wisps|2020|Plataforma|10
Palworld|2024|Survival|12
Pentiment|2022|Aventura|16
Persona 3 Reload|2024|RPG|14
Persona 5 Royal|2022|RPG|16
Psychonauts 2|2021|Plataforma|12
PUBG: Battlegrounds|2021|Battle Royale|16
Rainbow Six Siege|2020|FPS|18
Red Dead Redemption 2|2020|Mundo Aberto|18
Resident Evil 4|2023|Terror|18
Resident Evil Village|2021|Terror|18
Sea of Thieves|2020|Aventura|12
Senua's Saga: Hellblade II|2024|Acao|18
Star Wars Jedi: Survivor|2023|Aventura|12
Starfield|2023|RPG|16
Street Fighter 6|2023|Luta|12
Tekken 8|2024|Luta|12
The Elder Scrolls V: Skyrim Special Edition|2021|RPG|18
'@
    "Nintendo Switch" = @'
Animal Crossing: New Horizons|2020|Simulacao|3
Astral Chain|2019|Acao|14
Bayonetta 3|2022|Acao|18
Bayonetta Origins: Cereza and the Lost Demon|2023|Aventura|12
Captain Toad: Treasure Tracker|2018|Puzzle|3
Celeste|2018|Plataforma|10
Cuphead|2019|Plataforma|10
Dead Cells|2018|Acao|14
Donkey Kong Country: Tropical Freeze|2018|Plataforma|3
Dragon Quest XI S|2019|RPG|12
Fire Emblem: Three Houses|2019|Estrategia|12
Fire Emblem Engage|2023|Estrategia|12
Hades|2020|RPG|12
Hollow Knight|2018|Aventura|10
Kirby and the Forgotten Land|2022|Plataforma|7
Kirby Star Allies|2018|Plataforma|3
Luigi's Mansion 3|2019|Aventura|7
Mario + Rabbids Kingdom Battle|2017|Estrategia|7
Mario + Rabbids Sparks of Hope|2022|Estrategia|7
Mario Kart 8 Deluxe|2017|Corrida|3
Mario Tennis Aces|2018|Esporte|3
Metroid Dread|2021|Acao|12
Metroid Prime Remastered|2023|Aventura|12
Monster Hunter Rise|2021|RPG|12
New Pokemon Snap|2021|Simulacao|3
New Super Mario Bros. U Deluxe|2019|Plataforma|3
Octopath Traveler II|2023|RPG|12
Paper Mario: The Thousand-Year Door|2024|RPG|7
Paper Mario: The Origami King|2020|Aventura|7
Persona 5 Royal|2022|RPG|16
Pikmin 4|2023|Estrategia|3
Pokemon Legends: Arceus|2022|RPG|7
Pokemon Scarlet|2022|RPG|7
Pokemon Shield|2019|RPG|7
Pokemon Sword|2019|RPG|7
Pokemon Violet|2022|RPG|7
Princess Peach: Showtime!|2024|Plataforma|3
Splatoon 3|2022|FPS|10
Super Mario 3D World + Bowser's Fury|2021|Plataforma|3
Super Mario Bros. Wonder|2023|Plataforma|3
Super Mario Odyssey|2017|Plataforma|3
Super Mario Party Jamboree|2024|Puzzle|3
Super Mario Party|2018|Puzzle|3
Super Smash Bros. Ultimate|2018|Luta|10
Tetris Effect: Connected|2021|Puzzle|3
The Legend of Zelda: Breath of the Wild|2017|Aventura|10
The Legend of Zelda: Tears of the Kingdom|2023|Aventura|10
Triangle Strategy|2022|Estrategia|12
Unicorn Overlord|2024|Estrategia|12
Xenoblade Chronicles 3|2022|RPG|12
'@
    "PC" = @'
Age of Empires IV|2021|Estrategia|12
Alan Wake 2|2023|Terror|18
Apex Legends|2019|Battle Royale|16
Baldur's Gate 3|2023|RPG|16
Black Myth: Wukong|2024|RPG|16
Cities: Skylines II|2023|Simulacao|3
Civilization VI|2016|Estrategia|10
Counter-Strike 2|2023|FPS|16
Cyberpunk 2077|2020|RPG|18
Destiny 2|2017|FPS|16
Diablo IV|2023|RPG|18
Disco Elysium - The Final Cut|2021|RPG|16
Dota 2|2013|MOBA|12
Doom Eternal|2020|FPS|18
EA Sports FC 25|2024|Esporte|3
Elden Ring|2022|RPG|16
Euro Truck Simulator 2|2012|Simulacao|3
Factorio|2020|Estrategia|7
Football Manager 2024|2023|Simulacao|3
Grand Theft Auto V|2015|Mundo Aberto|18
Half-Life 2|2004|FPS|16
Half-Life: Alyx|2020|FPS|16
Hades|2020|RPG|12
Helldivers 2|2024|FPS|16
League of Legends|2009|MOBA|12
Marvel's Spider-Man Remastered|2022|Acao|12
Minecraft|2011|Sandbox|7
Monster Hunter World|2018|RPG|12
NieR: Automata|2017|RPG|18
Palworld|2024|Survival|12
Path of Exile|2013|RPG|16
Persona 3 Reload|2024|RPG|14
Persona 5 Royal|2022|RPG|16
Portal 2|2011|Puzzle|10
PUBG: Battlegrounds|2017|Battle Royale|16
Rainbow Six Siege|2015|FPS|18
Red Dead Redemption 2|2019|Mundo Aberto|18
Resident Evil 4|2023|Terror|18
Resident Evil Village|2021|Terror|18
RimWorld|2018|Simulacao|16
Rust|2018|Survival|16
Satisfactory|2024|Simulacao|7
Stardew Valley|2016|Simulacao|3
Street Fighter 6|2023|Luta|12
Tekken 8|2024|Luta|12
Terraria|2011|Sandbox|10
The Sims 4|2014|Simulacao|12
The Witcher 3: Wild Hunt|2015|RPG|18
Valheim|2021|Survival|16
Valorant|2020|FPS|16
'@
    "Atari 2600" = @'
Adventure|1980|Aventura|3
Air-Sea Battle|1977|Acao|3
Asteroids|1981|FPS|7
Atlantis|1982|Acao|7
Bank Heist|1983|Acao|7
Battlezone|1983|FPS|10
Berzerk|1982|FPS|10
Boxing|1980|Esporte|3
Breakout|1978|Puzzle|3
California Games|1987|Esporte|3
Centipede|1982|FPS|7
Chopper Command|1982|Acao|7
Combat|1977|Acao|3
Crystal Castles|1984|Puzzle|3
Dark Chambers|1988|RPG|10
Defender|1982|FPS|7
Demon Attack|1982|FPS|10
Dig Dug|1983|Puzzle|3
Donkey Kong|1982|Plataforma|3
Double Dunk|1989|Esporte|3
Enduro|1983|Corrida|3
Fatal Run|1990|Corrida|10
Fishing Derby|1980|Esporte|3
Football|1978|Esporte|3
Frogger|1982|Acao|3
Galaxian|1983|FPS|3
Gravitar|1983|Acao|7
Haunted House|1982|Terror|10
H.E.R.O.|1984|Acao|7
Joust|1983|Acao|7
Kaboom!|1981|Acao|3
Keystone Kapers|1983|Plataforma|3
Mario Bros.|1983|Plataforma|3
Millipede|1984|FPS|7
Missile Command|1981|Estrategia|3
Montezuma's Revenge|1984|Aventura|7
Moon Patrol|1983|Acao|3
Ms. Pac-Man|1982|Puzzle|3
Outlaw|1978|Acao|10
Pac-Man|1982|Puzzle|3
Phoenix|1983|FPS|3
Pitfall!|1982|Plataforma|3
Pole Position|1983|Corrida|3
Q*bert|1983|Puzzle|3
River Raid|1982|FPS|7
Seaquest|1983|Acao|7
Space Invaders|1980|FPS|3
Spider Fighter|1984|Acao|3
Star Raiders|1982|FPS|7
Yars' Revenge|1982|FPS|7
'@
    "Game Boy Color" = @'
Azure Dreams|2000|RPG|10
Bomberman Quest|2000|RPG|7
Bugs Bunny Crazy Castle 3|1999|Puzzle|3
Bionic Commando: Elite Forces|1999|Acao|10
Commander Keen|2001|Plataforma|3
Conker's Pocket Tales|1999|Plataforma|7
Crystalis|2000|RPG|10
Dance Dance Revolution GB 3|2000|Esporte|3
Dragon Ball Z: Legendary Super Warriors|2002|Estrategia|10
Dragon Warrior Monsters|2000|RPG|7
Dragon Warrior Monsters 2|2001|RPG|7
Driver|2000|Corrida|10
Evel Knievel|1999|Corrida|3
FIFA 2000|1999|Esporte|3
Game & Watch Gallery 2|1998|Puzzle|3
Game & Watch Gallery 3|1999|Puzzle|3
Grand Theft Auto 2|2000|Acao|16
Harvest Moon 3 GBC|2000|Simulacao|3
Hamtaro: Ham-Hams Unite!|2002|Aventura|3
Harry Potter and the Chamber of Secrets|2002|Aventura|7
Harry Potter and the Sorcerer's Stone|2001|Aventura|7
Kirby Tilt 'n' Tumble|2001|Puzzle|3
The Legend of Zelda: Link's Awakening DX|1998|Aventura|7
The Legend of Zelda: Oracle of Ages|2001|Aventura|7
The Legend of Zelda: Oracle of Seasons|2001|Aventura|7
Lufia: The Legend Returns|2001|RPG|10
Mario Golf|1999|Esporte|3
Mario Tennis|2001|Esporte|3
Mega Man Xtreme|2001|Acao|7
Mega Man Xtreme 2|2001|Acao|7
Metal Gear Solid|2000|Acao|12
Mickey's Racing Adventure|1999|Corrida|3
Mr. Driller|2000|Puzzle|3
NBA 3 on 3 Featuring Kobe Bryant|1999|Esporte|3
Pokemon Crystal|2001|RPG|3
Pokemon Gold|2000|RPG|3
Pokemon Pinball|1999|Puzzle|3
Pokemon Puzzle Challenge|2000|Puzzle|3
Pokemon Silver|2000|RPG|3
Rayman 2|2000|Plataforma|3
Resident Evil Gaiden|2001|Terror|12
R-Type DX|1999|FPS|7
Shantae|2002|Plataforma|7
Super Mario Bros. Deluxe|1999|Plataforma|3
Tetris DX|1998|Puzzle|3
Test Drive 2001|2000|Corrida|3
Tomb Raider: Curse of the Sword|2001|Aventura|10
Tony Hawk's Pro Skater 2|2001|Esporte|3
Wario Land 3|2000|Plataforma|3
Yu-Gi-Oh! Dark Duel Stories|2002|Estrategia|10
'@
    "Game Boy Advance" = @'
Advance Wars|2001|Estrategia|7
Advance Wars 2: Black Hole Rising|2003|Estrategia|7
Astro Boy: Omega Factor|2004|Acao|10
Boktai: The Sun Is in Your Hand|2003|Aventura|10
Castlevania: Aria of Sorrow|2003|Acao|12
Castlevania: Circle of the Moon|2001|Acao|12
Castlevania: Harmony of Dissonance|2002|Acao|12
ChuChu Rocket!|2001|Puzzle|3
Drill Dozer|2005|Plataforma|7
F-Zero: Maximum Velocity|2001|Corrida|3
F-Zero GP Legend|2004|Corrida|3
Final Fantasy Tactics Advance|2003|Estrategia|10
Fire Emblem|2003|Estrategia|12
Fire Emblem: The Sacred Stones|2004|Estrategia|12
Golden Sun|2001|RPG|7
Golden Sun: The Lost Age|2002|RPG|7
Gunstar Super Heroes|2005|Acao|10
Harvest Moon: Friends of Mineral Town|2003|Simulacao|3
Kingdom Hearts: Chain of Memories|2004|RPG|7
Kirby & The Amazing Mirror|2004|Plataforma|3
Kirby: Nightmare in Dream Land|2002|Plataforma|3
The Legend of Zelda: A Link to the Past & Four Swords|2002|Aventura|7
The Legend of Zelda: The Minish Cap|2004|Aventura|7
Mario & Luigi: Superstar Saga|2003|RPG|3
Mario Golf: Advance Tour|2004|Esporte|3
Mario Kart: Super Circuit|2001|Corrida|3
Mario Power Tennis|2005|Esporte|3
Mario Tennis: Power Tour|2005|Esporte|3
Mario vs. Donkey Kong|2004|Puzzle|3
Mega Man Battle Network 2|2002|RPG|7
Mega Man Battle Network 3 Blue|2002|RPG|7
Mega Man Battle Network 6 Cybeast Falzar|2005|RPG|7
Metroid Fusion|2002|Acao|12
Metroid: Zero Mission|2004|Acao|12
Mother 3|2006|RPG|10
Pokemon Emerald|2005|RPG|3
Pokemon FireRed|2004|RPG|3
Pokemon LeafGreen|2004|RPG|3
Pokemon Ruby|2003|RPG|3
Pokemon Sapphire|2003|RPG|3
Sonic Advance|2002|Plataforma|3
Sonic Advance 2|2002|Plataforma|3
Sonic Advance 3|2004|Plataforma|3
Super Mario Advance 2|2002|Plataforma|3
Super Mario Advance 3|2003|Plataforma|3
Super Mario Advance 4|2003|Plataforma|3
Tactics Ogre: The Knight of Lodis|2002|Estrategia|12
WarioWare, Inc.: Mega Microgames!|2003|Puzzle|3
Yoshi's Island: Super Mario Advance 3|2002|Plataforma|3
Yu-Gi-Oh! The Eternal Duelist Soul|2002|Estrategia|10
'@
    "PlayStation 1" = @'
Ace Combat 3: Electrosphere|2000|FPS|10
Ape Escape|1999|Plataforma|3
Breath of Fire III|1998|RPG|10
Breath of Fire IV|2000|RPG|10
Castlevania: Symphony of the Night|1997|Acao|12
Chrono Cross|2000|RPG|10
Crash Bandicoot|1996|Plataforma|3
Crash Bandicoot 2: Cortex Strikes Back|1997|Plataforma|3
Crash Bandicoot 3: Warped|1998|Plataforma|3
Crash Team Racing|1999|Corrida|3
Dino Crisis|1999|Terror|16
Dino Crisis 2|2000|Terror|16
Driver|1999|Corrida|10
Driver 2|2000|Corrida|10
Final Fantasy VII|1997|RPG|12
Final Fantasy VIII|1999|RPG|12
Final Fantasy IX|2000|RPG|12
Gran Turismo|1998|Corrida|3
Gran Turismo 2|1999|Corrida|3
Klonoa: Door to Phantomile|1998|Plataforma|3
Legacy of Kain: Soul Reaver|1999|Acao|16
Mega Man Legends|1998|Acao|7
Metal Gear Solid|1998|Acao|16
Oddworld: Abe's Exoddus|1998|Plataforma|10
Oddworld: Abe's Oddysee|1997|Plataforma|10
Parasite Eve|1998|RPG|16
PaRappa the Rapper|1997|Puzzle|3
Rayman|1995|Plataforma|3
Resident Evil|1996|Terror|16
Resident Evil 2|1998|Terror|18
Resident Evil 3: Nemesis|1999|Terror|18
Ridge Racer Type 4|1999|Corrida|3
Silent Hill|1999|Terror|18
Spyro the Dragon|1998|Plataforma|3
Spyro 2: Ripto's Rage!|1999|Plataforma|3
Spyro: Year of the Dragon|2000|Plataforma|3
Suikoden|1996|RPG|12
Suikoden II|1999|RPG|12
Tekken 2|1996|Luta|12
Tekken 3|1998|Luta|12
Tenchu: Stealth Assassins|1998|Acao|16
Tomb Raider|1996|Aventura|10
Tomb Raider II|1997|Aventura|10
Tony Hawk's Pro Skater 2|2000|Esporte|3
Twisted Metal 2|1996|Acao|12
Vagrant Story|2000|RPG|16
Wipeout|1995|Corrida|3
Wipeout XL|1996|Corrida|3
Xenogears|1998|RPG|12
Yu-Gi-Oh! Forbidden Memories|2002|Estrategia|10
'@
    "PlayStation 2" = @'
Beyond Good & Evil|2003|Aventura|10
Black|2006|FPS|16
Burnout 3: Takedown|2004|Corrida|10
Bully|2006|Mundo Aberto|16
Dark Cloud|2001|RPG|10
Dark Cloud 2|2003|RPG|10
Devil May Cry|2001|Acao|16
Devil May Cry 3: Dante's Awakening|2005|Acao|18
Dragon Ball Z: Budokai 3|2004|Luta|12
Dragon Quest VIII|2005|RPG|10
Final Fantasy X|2001|RPG|12
Final Fantasy X-2|2003|RPG|12
God Hand|2006|Acao|16
God of War|2005|Acao|18
God of War II|2007|Acao|18
Gran Turismo 3: A-Spec|2001|Corrida|3
Gran Turismo 4|2005|Corrida|3
Guitar Hero II|2006|Puzzle|3
Ico|2001|Aventura|10
Jak and Daxter: The Precursor Legacy|2001|Plataforma|3
Jak II|2003|Acao|12
Katamari Damacy|2004|Puzzle|3
Kingdom Hearts|2002|RPG|7
Kingdom Hearts II|2005|RPG|10
Klonoa 2: Lunatea's Veil|2001|Plataforma|3
Metal Gear Solid 2: Sons of Liberty|2001|Acao|16
Metal Gear Solid 3: Snake Eater|2004|Acao|16
Midnight Club 3: DUB Edition|2005|Corrida|10
Mortal Kombat: Shaolin Monks|2005|Acao|18
Need for Speed: Most Wanted|2005|Corrida|10
Okami|2006|Aventura|10
Persona 3 FES|2007|RPG|16
Persona 4|2008|RPG|16
Prince of Persia: The Sands of Time|2003|Aventura|12
Ratchet & Clank|2002|Plataforma|3
Ratchet & Clank: Up Your Arsenal|2004|Plataforma|3
Resident Evil 4|2005|Terror|18
Shadow of the Colossus|2005|Aventura|12
Shin Megami Tensei: Nocturne|2004|RPG|16
Silent Hill 2|2001|Terror|18
Silent Hill 3|2003|Terror|18
SOCOM II: U.S. Navy SEALs|2003|FPS|16
SoulCalibur II|2003|Luta|12
SSX 3|2003|Esporte|3
Tekken 5|2005|Luta|12
The Simpsons: Hit & Run|2003|Mundo Aberto|10
TimeSplitters 2|2002|FPS|16
Tony Hawk's Underground|2003|Esporte|12
We Love Katamari|2005|Puzzle|3
Yakuza|2006|Acao|18
'@
    "Super Nintendo" = @'
ActRaiser|1991|Acao|7
Axelay|1992|FPS|7
Breath of Fire II|1995|RPG|10
Chrono Trigger|1995|RPG|10
Contra III: The Alien Wars|1992|Acao|10
Donkey Kong Country|1994|Plataforma|3
Donkey Kong Country 2: Diddy's Kong Quest|1995|Plataforma|3
Donkey Kong Country 3: Dixie Kong's Double Trouble!|1996|Plataforma|3
EarthBound|1995|RPG|10
F-Zero|1991|Corrida|3
Final Fantasy II|1991|RPG|10
Final Fantasy III|1994|RPG|10
Final Fight|1991|Acao|10
Fire Emblem: Genealogy of the Holy War|1996|Estrategia|12
Harvest Moon|1997|Simulacao|3
International Superstar Soccer Deluxe|1995|Esporte|3
Kirby Super Star|1996|Plataforma|3
Kirby's Dream Course|1994|Puzzle|3
The Legend of Zelda: A Link to the Past|1991|Aventura|7
Lufia II: Rise of the Sinistrals|1996|RPG|10
Mega Man X|1993|Acao|7
Mega Man X2|1994|Acao|7
Mega Man X3|1995|Acao|7
Mortal Kombat II|1993|Luta|16
NBA Jam|1993|Esporte|3
Ogre Battle: The March of the Black Queen|1993|Estrategia|12
Pilotwings|1991|Simulacao|3
Rock n' Roll Racing|1993|Corrida|3
Secret of Evermore|1995|RPG|10
Secret of Mana|1993|RPG|10
Star Fox|1993|FPS|7
Street Fighter II Turbo|1993|Luta|12
Sunset Riders|1993|Acao|10
Super Bomberman|1993|Puzzle|3
Super Castlevania IV|1991|Acao|10
Super Ghouls 'n Ghosts|1991|Acao|10
Super Mario All-Stars|1993|Plataforma|3
Super Mario Kart|1992|Corrida|3
Super Mario RPG|1996|RPG|7
Super Mario World|1990|Plataforma|3
Super Mario World 2: Yoshi's Island|1995|Plataforma|3
Super Metroid|1994|Acao|10
Super Punch-Out!!|1994|Esporte|3
Super Puyo Puyo 2|1995|Puzzle|3
Teenage Mutant Ninja Turtles IV: Turtles in Time|1992|Acao|7
Terranigma|1995|RPG|10
Tetris Attack|1995|Puzzle|3
Top Gear|1992|Corrida|3
Uniracers|1994|Corrida|3
Wild Guns|1994|FPS|10
'@
    "NES 8 Bits" = @'
Adventure Island|1988|Plataforma|3
Balloon Fight|1985|Acao|3
Batman|1989|Acao|7
Battle City|1985|Acao|3
Bionic Commando|1988|Acao|7
Blaster Master|1988|Acao|7
Bubble Bobble|1986|Puzzle|3
Castlevania|1986|Acao|10
Castlevania III: Dracula's Curse|1989|Acao|10
Chip 'n Dale Rescue Rangers|1990|Plataforma|3
Contra|1988|Acao|10
Crystalis|1990|RPG|10
Double Dragon II: The Revenge|1989|Acao|10
Dr. Mario|1990|Puzzle|3
DuckTales|1989|Plataforma|3
Excitebike|1985|Corrida|3
Faxanadu|1989|RPG|10
Final Fantasy|1990|RPG|10
Galaga|1988|FPS|3
Ghosts 'n Goblins|1986|Acao|10
Gradius|1986|FPS|7
Ice Climber|1985|Plataforma|3
Journey to Silius|1990|Acao|10
Kid Icarus|1987|Plataforma|7
Kirby's Adventure|1993|Plataforma|3
The Legend of Zelda|1987|Aventura|7
Little Nemo: The Dream Master|1990|Plataforma|3
Mega Man 2|1989|Acao|7
Mega Man 3|1990|Acao|7
Metal Gear|1988|Acao|12
Metroid|1987|Acao|10
Mike Tyson's Punch-Out!!|1987|Esporte|3
Ninja Gaiden|1989|Acao|12
Paperboy|1988|Acao|3
Punch-Out!!|1987|Esporte|3
River City Ransom|1990|Acao|10
R.C. Pro-Am|1988|Corrida|3
Shadowgate|1989|Aventura|10
StarTropics|1990|Aventura|7
Super C|1990|Acao|10
Super Mario Bros.|1985|Plataforma|3
Super Mario Bros. 2|1988|Plataforma|3
Super Mario Bros. 3|1990|Plataforma|3
Tecmo Bowl|1989|Esporte|3
Tecmo Super Bowl|1991|Esporte|3
Tetris|1989|Puzzle|3
Teenage Mutant Ninja Turtles II: The Arcade Game|1990|Acao|7
The Guardian Legend|1989|FPS|10
Zelda II: The Adventure of Link|1988|Aventura|10
Duck Hunt|1985|FPS|3
'@
}

function Convert-ToSlug {
    param([string]$Text)

    $slug = $Text.ToLowerInvariant()
    $slug = $slug -replace "[^a-z0-9]+", "-"
    $slug = $slug.Trim("-")
    return $slug
}

function Escape-Sql {
    param([string]$Text)

    return $Text.Replace("'", "''")
}

function Escape-Xml {
    param([string]$Text)

    return [System.Security.SecurityElement]::Escape($Text)
}

function Wrap-Title {
    param(
        [string]$Text,
        [int]$MaxLength = 18
    )

    $words = $Text.Split(" ")
    $lines = New-Object System.Collections.Generic.List[string]
    $current = ""

    foreach ($word in $words) {
        if ([string]::IsNullOrWhiteSpace($current)) {
            $current = $word
            continue
        }

        if (($current.Length + 1 + $word.Length) -le $MaxLength) {
            $current = "$current $word"
        } else {
            $lines.Add($current)
            $current = $word
        }
    }

    if (-not [string]::IsNullOrWhiteSpace($current)) {
        $lines.Add($current)
    }

    return $lines
}

function New-GameSvg {
    param(
        [string]$Title,
        [string]$Platform,
        [string]$Genre,
        [string]$Year,
        [hashtable]$Config
    )

    $titleLines = Wrap-Title -Text $Title
    $titleY = 120
    $titleSvg = New-Object System.Text.StringBuilder
    foreach ($line in $titleLines) {
        [void]$titleSvg.AppendLine("  <text x=""36"" y=""$titleY"" font-size=""28"" font-weight=""700"" fill=""$($Config.Accent)"">$([System.Security.SecurityElement]::Escape($line))</text>")
        $titleY += 34
    }

    return @"
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 840" role="img" aria-label="$(Escape-Xml $Title)">
  <defs>
    <linearGradient id="bg" x1="0%" y1="0%" x2="100%" y2="100%">
      <stop offset="0%" stop-color="$($Config.Primary)"/>
      <stop offset="100%" stop-color="$($Config.Secondary)"/>
    </linearGradient>
  </defs>
  <rect width="600" height="840" rx="36" fill="url(#bg)"/>
  <circle cx="500" cy="120" r="110" fill="rgba(255,255,255,0.08)"/>
  <circle cx="90" cy="740" r="150" fill="rgba(0,0,0,0.12)"/>
  <text x="36" y="64" font-size="18" letter-spacing="4" fill="$($Config.Accent)" opacity="0.9">LOCADORA PRIME</text>
$titleSvg
  <text x="36" y="650" font-size="22" fill="$($Config.Accent)" opacity="0.95">PLATAFORMA</text>
  <text x="36" y="682" font-size="30" font-weight="700" fill="$($Config.Accent)">$([System.Security.SecurityElement]::Escape($Platform))</text>
  <text x="36" y="728" font-size="22" fill="$($Config.Accent)" opacity="0.95">GENERO</text>
  <text x="36" y="760" font-size="28" font-weight="700" fill="$($Config.Accent)">$([System.Security.SecurityElement]::Escape($Genre))</text>
  <text x="470" y="760" text-anchor="end" font-size="42" font-weight="700" fill="$($Config.Accent)">$Year</text>
</svg>
"@
}

$games = New-Object System.Collections.Generic.List[object]
$id = 1

foreach ($platform in $platformOrder) {
    $config = $platformConfigs[$platform]
    $platformDir = Join-Path $imagesRoot $config.Slug
    New-Item -ItemType Directory -Force -Path $platformDir | Out-Null

    $lines = $seedData[$platform].Trim().Split("`n")
    foreach ($line in $lines) {
        $parts = $line.Trim().Split("|")
        if ($parts.Count -ne 4) {
            throw "Linha invalida para ${platform}: $line"
        }

        $title = $parts[0].Trim()
        $year = $parts[1].Trim()
        $genre = $parts[2].Trim()
        $ageRating = [int]$parts[3].Trim()
        $slug = Convert-ToSlug $title
        $imageRelativePath = "resources/demo/images/jogos/$($config.Slug)/$slug.svg"
        $imageFilePath = Join-Path $platformDir "$slug.svg"

        $price = [math]::Round(11.9 + (($id * 7) % 17) + ($platformOrder.IndexOf($platform) * 1.25), 2)
        $isPromotion = ($id % 4 -eq 0)
        $promotionValue = if ($isPromotion) { [math]::Round([math]::Max($price - 4.0, 9.9), 2) } else { $null }
        $isAvailable = ($id % 9 -ne 0)

        $svgContent = New-GameSvg -Title $title -Platform $platform -Genre $genre -Year $year -Config $config
        Set-Content -Path $imageFilePath -Value $svgContent -Encoding UTF8

        $games.Add([PSCustomObject]@{
            Id = $id
            Nome = $title
            Valor = $price
            Disponivel = $(if ($isAvailable) { "Sim" } else { "Nao" })
            Promocao = $(if ($isPromotion) { "Sim" } else { "Nao" })
            ValorPromocao = $promotionValue
            Plataforma = $platform
            AnoLancamento = $year
            FaixaEtaria = $ageRating
            Genero = $genre
            Imagem = $imageRelativePath
        })

        $id++
    }
}

if ($games.Count -ne 550) {
    throw "Quantidade inesperada de jogos gerados: $($games.Count)"
}

$sqlLines = New-Object System.Collections.Generic.List[string]
for ($index = 0; $index -lt $games.Count; $index++) {
    $game = $games[$index]
    $separator = if ($index -lt ($games.Count - 1)) { "," } else { ";" }
    $valorPromocao = if ($null -eq $game.ValorPromocao) { "NULL" } else { $game.ValorPromocao.ToString([System.Globalization.CultureInfo]::InvariantCulture) }
    $line = "    ({0}, '{1}', {2}, '{3}', '{4}', {5}, '{6}', '{7}', {8}, '{9}', '{10}'){11}" -f `
        $game.Id, `
        (Escape-Sql $game.Nome), `
        $game.Valor.ToString([System.Globalization.CultureInfo]::InvariantCulture), `
        $game.Disponivel, `
        $game.Promocao, `
        $valorPromocao, `
        (Escape-Sql $game.Plataforma), `
        $game.AnoLancamento, `
        $game.FaixaEtaria, `
        (Escape-Sql $game.Genero), `
        $game.Imagem, `
        $separator
    $sqlLines.Add($line)
}

$jogoBlockTemplate = @'
-- Copiando dados para a tabela dtb-locadora-rich.jogo: ~550 rows (aproximadamente)
/*!40000 ALTER TABLE `jogo` DISABLE KEYS */;
INSERT INTO `jogo` (`id_jogo`, `nome`, `valor`, `disponivel`, `promocao`, `valor_promocao`, `plataforma`, `ano_lancamento`, `faixa_etaria`, `genero`, `imagem`) VALUES
__VALUES__
/*!40000 ALTER TABLE `jogo` ENABLE KEYS */;
'@

$jogoBlock = $jogoBlockTemplate.Replace("__VALUES__", ($sqlLines -join "`r`n"))

$blockPattern = '(?s)-- Copiando dados para a tabela dtb-locadora-rich\.jogo:.*?(?=\r?\n-- Copiando estrutura para tabela dtb-locadora-rich\.forma_pagamento)'

foreach ($sqlFile in $sqlFiles) {
    $content = Get-Content -Raw -Path $sqlFile
    $updated = $content
    foreach ($tableName in $schemaBlocks.Keys) {
        $schemaBlock = $schemaBlocks[$tableName].Trim()
        $pattern = "(?s)(-- Copiando estrutura para tabela dtb-locadora-rich\.${tableName}\s*\r?\n).*?(\r?\n-- Copiando dados para a tabela dtb-locadora-rich\.${tableName}:)"
        $updated = [regex]::Replace(
            $updated,
            $pattern,
            {
                param($match)
                $match.Groups[1].Value + $schemaBlock + "`r`n`r`n" + $match.Groups[2].Value
            },
            1
        )
    }
    $updated = [regex]::Replace($updated, $blockPattern, $jogoBlock + "`r`n", 1)
    Set-Content -Path $sqlFile -Value $updated -Encoding UTF8
}

Write-Host "Seed gerada com $($games.Count) jogos e capas SVG locais."
