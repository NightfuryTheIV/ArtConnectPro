use ProjectDB;


-- =======================================
-- 1. DISCIPLINES
-- =======================================
INSERT INTO Discipline (DName) VALUES
  ('Painting'),
  ('Sculpture'),
  ('Photography'),
  ('Digital Art'),
  ('Ceramics'),
  ('Printmaking'),
  ('Installation Art');

-- =======================================
-- 2. ARTISTS  (6 artists with varied profiles)
-- =======================================
INSERT INTO Artists
  (ArtistID, AName, ACity, AEmail, ABirthyear, ABio, APhone, AWebsite, ASocialMedia, isActive)
VALUES
  (1, 'Elena Moreau',    'Paris',      'elena@moreau-art.fr',    '1985-03-12',
   'Multidisciplinary artist blending painting and digital media.',
   '+33 6 10 11 12 13', 'www.moreau-art.fr',    '@elena_moreau',    TRUE),
  (2, 'Karim Osei',      'Lyon',       'karim@oseistudio.fr',    '1978-07-22',
   'Sculptor and ceramist with a focus on African heritage motifs.',
   '+33 6 20 21 22 23', 'www.oseistudio.fr',    '@karim_osei',      TRUE),
  (3, 'Yuki Tanaka',     'Bordeaux',   'yuki@tanaka-photo.fr',   '1990-11-05',
   'Documentary photographer exploring urban landscapes.',
   '+33 6 30 31 32 33', 'www.tanaka-photo.fr',  '@yuki_tanaka',     TRUE),
  (4, 'Sophie Renard',   'Marseille',  'sophie@renard-prints.fr','1982-05-18',
   'Printmaker and installation artist; exhibited in 12 countries.',
   '+33 6 40 41 42 43', 'www.renard-prints.fr', '@sophie_renard',   TRUE),
  (5, 'Marco Benedetti', 'Nice',       'marco@benedetti-art.it', '1975-09-30',
   'Italian-born painter settled in Nice; known for seascape oils.',
   '+33 6 50 51 52 53', 'www.benedetti-art.it', '@marco_benedetti', TRUE),
  (6, 'Ines Dupuis',     'Toulouse',   'ines@dupuis-digital.fr', '1995-02-14',
   'New-generation digital artist and NFT creator.',
   '+33 6 60 61 62 63', 'www.dupuis-digital.fr','@ines_dupuis',     FALSE);

-- =======================================
-- Artist ↔ Discipline (multi-discipline cases)
-- =======================================
INSERT INTO Has (ArtistID, DName) VALUES
  (1, 'Painting'),      -- Elena: Painting + Digital Art
  (1, 'Digital Art'),
  (2, 'Sculpture'),     -- Karim: Sculpture + Ceramics
  (2, 'Ceramics'),
  (3, 'Photography'),   -- Yuki: Photography only
  (4, 'Printmaking'),   -- Sophie: Printmaking + Installation Art
  (4, 'Installation Art'),
  (5, 'Painting'),      -- Marco: Painting only
  (6, 'Digital Art'),   -- Ines: Digital Art + Photography
  (6, 'Photography');

-- =======================================
-- 3. GALLERIES  (3 venues)
-- =======================================
INSERT INTO Gallery
  (GalleryID, GName, GAddress, GRating, GOwnerName, GOpeningHours, GContactPhone, GWebsite)
VALUES
  (1, 'Galerie Lumière',  '14 Rue de Rivoli, Paris',    0.93,
   'Hélène Voss',   'Tue-Sun 10:00-19:00', '+33 1 40 10 20 30', 'www.galerie-lumiere.fr'),
  (2, 'Espace Belvédère', '3 Allée des Arts, Lyon',     0.87,
   'Antoine Farge',  'Wed-Mon 09:00-18:00', '+33 4 72 10 20 30', 'www.espace-belvedere.fr'),
  (3, 'Studio Azur',      '88 Promenade des Arts, Nice', 0.78,
   'Camille Lotti',  'Mon-Sat 11:00-20:00', '+33 4 93 10 20 30', 'www.studio-azur.fr');

-- =======================================
-- 4. EXHIBITIONS  (4 exhibitions across the 3 galleries)
-- =======================================
INSERT INTO Exhibitions
  (ExhibitionID, EXTitle, EXStartDate, EXTheme, EXEndDate, EXDescription, EXCuratorName, GalleryID)
VALUES
  (1, 'Lumières Numériques',
   '2024-03-01', 'Digital & Painting Fusion',   '2024-04-30',
   'A dialogue between classical painting and contemporary digital creation.',
   'Marie Leclerc', 1),
  (2, 'Terres & Formes',
   '2024-04-15', 'Sculpture and Ceramics',       '2024-06-15',
   'Exploring three-dimensional form through clay, stone and mixed media.',
   'Jean-Paul Morin', 2),
  (3, 'Regards Urbains',
   '2024-06-01', 'Urban Photography',            '2024-07-31',
   'Photographic portraits of European cities in transition.',
   'Clara Weiss', 2),
  (4, 'Entre Lignes',
   '2024-09-01', 'Print & Installation',         '2024-11-30',
   'From the printing press to the immersive space — a continuum of mark-making.',
   'Nathalie Blondel', 1);

-- =======================================
-- 5. ARTWORK TAGS
-- =======================================
INSERT INTO ArtworksTag (AWTName) VALUES
  ('Abstract'),
  ('Portrait'),
  ('Landscape'),
  ('Urban'),
  ('Nature'),
  ('Political'),
  ('Minimalist'),
  ('Experimental'),
  ('Heritage'),
  ('Seascape');

-- =======================================
-- 6. ARTWORKS  (12 artworks, spread across exhibitions/artists)
-- =======================================
INSERT INTO Artworks
  (ArtworkID, AWTitle, AWType, AWPrice, AWStatus, AWCreationYear,
   AWMedium, AWDimensions, AWDescription, ExhibitionID, ArtistID)
VALUES
  -- Exhibition 1 – Lumières Numériques
  (1,  'Éclats de Pixels',       'Digital Print',  1200.00, 'Available', 2023,
   'Inkjet print', '80x120cm',  'Fragmented urban grids rendered as pixel-burst explosions.', 1, 1),
  (2,  'Mémoire Vive',           'Painting',       3500.00, 'Sold',      2022,
   'Oil on canvas','100x150cm', 'Layered oil painting referencing computer memory architecture.', 1, 1),
  (3,  'Ghost Network',          'Digital Art',     950.00, 'Available', 2024,
   'Generative',  '60x90cm',   'AI-generated network graph printed on aluminium.', 1, 6),

  -- Exhibition 2 – Terres & Formes
  (4,  'Ancêtre #3',             'Sculpture',      4800.00, 'Available', 2021,
   'Bronze',       '45x30x20cm','Third in a series inspired by Ashanti ancestor figures.', 2, 2),
  (5,  'Vase Rituel',            'Ceramics',        620.00, 'Available', 2023,
   'Stoneware',    '30x18cm',   'High-fired stoneware with oxide washes recalling kente patterns.', 2, 2),
  (6,  'Fragment de Ville',      'Ceramics',        480.00, 'Sold',      2023,
   'Earthenware',  '25x25cm',   'Ceramic tile mosaic mapping a fictional city district.', 2, 2),

  -- Exhibition 3 – Regards Urbains
  (7,  'Gare du Nord, 6h12',     'Photography',     750.00, 'Available', 2023,
   'C-print',      '60x90cm',   'Long-exposure dawn commuters at Paris\'s busiest station.', 3, 3),
  (8,  'Murs Parlants',          'Photography',     680.00, 'Available', 2024,
   'Archival inkjet','50x75cm', 'Street art documentation across 5 European capitals.', 3, 3),
  (9,  'Suburbia Bleu',          'Photography',     820.00, 'Sold',      2022,
   'Silver gelatin','70x100cm', 'Twilight suburban peripheries shot on medium format film.', 3, 3),

  -- Exhibition 4 – Entre Lignes
  (10, 'Sérigraphie Rouge #7',   'Printmaking',     390.00, 'Available', 2023,
   'Screen print',  '50x70cm',  'Seven-layer screen print exploring protest poster aesthetics.', 4, 4),
  (11, 'Corridor Infini',        'Installation',   6500.00, 'Available', 2024,
   'Mixed media',   '300x80cm', 'A corridor of overlapping prints creating infinite-mirror effect.', 4, 4),
  (12, 'Méditerranée No. 4',     'Painting',       2800.00, 'Available', 2023,
   'Oil on linen',  '90x130cm', 'Fourth in a seascape series capturing the Nice coast at dusk.', 4, 5);

-- =======================================
-- 7. COMMUNITY MEMBERS  (8 members, mixed membership types)
-- =======================================
INSERT INTO CommunityMember
  (MemberID, CMName, CMEmail, CMCity, CMBirthyear, CMPhone, MembershipType)
VALUES
  (1, 'Alice Fontaine',  'alice@mail.fr',   'Paris',     '1988-04-02', '+33 6 01 11 22 33', 'Premium'),
  (2, 'Bruno Sauvage',   'bruno@mail.fr',   'Lyon',      '1975-08-19', '+33 6 02 11 22 33', 'Standard'),
  (3, 'Céline Morel',    'celine@mail.fr',  'Bordeaux',  '1992-12-25', '+33 6 03 11 22 33', 'Premium'),
  (4, 'David Kante',     'david@mail.fr',   'Marseille', '1983-03-07', '+33 6 04 11 22 33', 'Basic'),
  (5, 'Emma Leclaire',   'emma@mail.fr',    'Nice',      '1997-06-14', '+33 6 05 11 22 33', 'Standard'),
  (6, 'Félix Garnier',   'felix@mail.fr',   'Toulouse',  '1969-11-30', '+33 6 06 11 22 33', 'Premium'),
  (7, 'Gaëlle Perrin',   'gaelle@mail.fr',  'Paris',     '2001-01-08', '+33 6 07 11 22 33', 'Basic'),
  (8, 'Hugo Descartes',  'hugo@mail.fr',    'Lyon',      '1986-09-21', '+33 6 08 11 22 33', 'Standard');

-- Member ↔ Discipline preferences
INSERT INTO Likes (MemberID, DName) VALUES
  (1, 'Painting'),     (1, 'Digital Art'),
  (2, 'Sculpture'),    (2, 'Ceramics'),
  (3, 'Photography'),  (3, 'Printmaking'),
  (4, 'Ceramics'),
  (5, 'Digital Art'),  (5, 'Photography'),
  (6, 'Painting'),     (6, 'Installation Art'),
  (7, 'Photography'),
  (8, 'Printmaking'),  (8, 'Sculpture');

-- =======================================
-- 8. WORKSHOPS  (5 workshops, mixed levels & prices)
-- =======================================
INSERT INTO Workshops
  (WorkshopID, WSTitle, WSDate, WSPrice, WSLevel, DurationMinutes,
   MaxParticipants, WSLocation, WSDescription)
VALUES
  (1, 'Introduction à la Peinture Numérique',
   '2024-05-10', 45.00, 'Beginner',     180, 15,
   'Galerie Lumière, Paris',
   'Hands-on Procreate session with Elena Moreau.'),

  (2, 'Céramique Tournée – Niveau Intermédiaire',
   '2024-05-25', 65.00, 'Intermediate', 240, 10,
   'Espace Belvédère, Lyon',
   'Wheel-throwing techniques with Karim Osei.'),

  (3, 'Photographie Urbaine en Pratique',
   '2024-06-08', 55.00, 'Beginner',     300, 12,
   'City Centre, Bordeaux',
   'Urban shooting walk with Yuki Tanaka, plus editing review.'),

  (4, 'Sérigraphie Avancée',
   '2024-07-20', 90.00, 'Advanced',     360, 8,
   'Studio Azur, Nice',
   'Advanced multi-layer screen printing with Sophie Renard.'),

  (5, 'Peinture à l\'Huile – Paysages Marins',
   '2024-08-03', 75.00, 'Intermediate', 300, 10,
   'Studio Azur, Nice',
   'Plein-air seascape painting session led by Marco Benedetti.');

-- Artist cross-participation (artists attend workshops NOT led by themselves)
INSERT INTO Participates_in (ArtistID, WorkshopID) VALUES
  (1, 2),  -- Elena attends Karim's ceramics WS
  (1, 3),  -- Elena attends Yuki's photography WS
  (2, 1),  -- Karim attends Elena's digital painting WS
  (2, 5),  -- Karim attends Marco's seascape WS
  (3, 1),  -- Yuki attends Elena's digital painting WS
  (3, 4),  -- Yuki attends Sophie's screen printing WS
  (4, 2),  -- Sophie attends Karim's ceramics WS
  (4, 3),  -- Sophie attends Yuki's photography WS
  (5, 1),  -- Marco attends Elena's digital painting WS
  (5, 4),  -- Marco attends Sophie's screen printing WS
  (6, 1),  -- Ines attends Elena's digital painting WS
  (6, 3);  -- Ines attends Yuki's photography WS

-- =======================================
-- 9. BOOKINGS  (members book workshops; varied payment states)
-- =======================================
INSERT INTO Booking (BookingID, BookingDate, PaymentStatus, WorkshopID, MemberID)
VALUES
  -- Workshop 1 – 5 bookings
  (1,  '2024-04-20', 'Confirmed', 1, 1),
  (2,  '2024-04-21', 'Confirmed', 1, 3),
  (3,  '2024-04-22', 'Pending',   1, 5),
  (4,  '2024-04-23', 'Confirmed', 1, 7),
  (5,  '2024-04-24', 'Cancelled', 1, 2),

  -- Workshop 2 – 4 bookings
  (6,  '2024-05-01', 'Confirmed', 2, 2),
  (7,  '2024-05-02', 'Confirmed', 2, 4),
  (8,  '2024-05-03', 'Pending',   2, 6),
  (9,  '2024-05-04', 'Confirmed', 2, 8),

  -- Workshop 3 – 4 bookings
  (10, '2024-05-15', 'Confirmed', 3, 3),
  (11, '2024-05-16', 'Confirmed', 3, 5),
  (12, '2024-05-17', 'Refunded',  3, 1),
  (13, '2024-05-18', 'Confirmed', 3, 7),

  -- Workshop 4 – 3 bookings (advanced, fewer takers)
  (14, '2024-06-25', 'Confirmed', 4, 6),
  (15, '2024-06-26', 'Confirmed', 4, 8),
  (16, '2024-06-27', 'Pending',   4, 1),

  -- Workshop 5 – 3 bookings
  (17, '2024-07-05', 'Confirmed', 5, 5),
  (18, '2024-07-06', 'Confirmed', 5, 2),
  (19, '2024-07-07', 'Cancelled', 5, 4);

-- =======================================
-- 10. REVIEWS  (members review artworks they have seen)
-- =======================================
-- Artworks from Exhibition 1 (Digital & Painting)
INSERT INTO Review (ReviewID, Rating, Comment, ReviewDate, ArtworkID, MemberID)
VALUES
  (1,  5.0, 'Éclats de Pixels perfectly encapsulates the chaos and beauty of city grids.',
   '2024-04-10', 1, 1),
  (2,  4.0, 'Mémoire Vive is conceptually rich; wish the palette were bolder.',
   '2024-04-12', 2, 3),
  (3,  4.0, 'Ghost Network feels fresh and provocative.',
   '2024-04-15', 3, 5),
  (4,  3.0, 'Interesting but feels overly technical for a gallery setting.',
   '2024-04-18', 3, 7),

  -- Artworks from Exhibition 2 (Sculpture & Ceramics)
  (5,  5.0, 'Ancêtre #3 is breathtaking. The surface patina is extraordinary.',
   '2024-05-20', 4, 2),
  (6,  4.0, 'Vase Rituel is beautifully executed; the oxide washes are subtle.',
   '2024-05-22', 5, 4),
  (7,  3.0, 'Fragment de Ville is clever but the narrative feels incomplete.',
   '2024-05-25', 6, 6),
  (8,  5.0, 'Karim Osei has a unique command of form and cultural reference.',
   '2024-05-28', 4, 8),

  -- Artworks from Exhibition 3 (Photography)
  (9,  5.0, 'Gare du Nord 6h12 is hauntingly poetic. The light is perfect.',
   '2024-06-20', 7, 3),
  (10, 4.0, 'Murs Parlants documents ephemeral art with real sensitivity.',
   '2024-06-22', 8, 1),
  (11, 4.0, 'Suburbia Bleu captures a melancholy that lingers.',
   '2024-06-25', 9, 5),
  (12, 3.0, 'Technically solid but the subject feels overly familiar.',
   '2024-06-28', 8, 7),

  -- Artworks from Exhibition 4 (Print & Installation)
  (13, 5.0, 'Corridor Infini is immersive and unforgettable.',
   '2024-10-05', 11, 6),
  (14, 4.0, 'Sérigraphie Rouge #7 is graphic and urgent — exactly right.',
   '2024-10-08', 10, 2),
  (15, 5.0, 'Méditerranée No. 4 makes me want to be on that beach immediately.',
   '2024-10-10', 12, 1),
  (16, 4.0, 'Marco paints light like no one else in this show.',
   '2024-10-12', 12, 5);