DROP DATABASE IF EXISTS ProjectDB;
CREATE DATABASE ProjectDB;

USE ProjectDB;



CREATE TABLE Artists (
    ArtistID INT PRIMARY KEY,
    AName VARCHAR(50),
    ACity VARCHAR(50),
    AEmail VARCHAR(50),
    ABirthyear DATE,
    ABio VARCHAR(200),
    APhone VARCHAR(20),
    AWebsite VARCHAR(100),
    ASocialMedia VARCHAR(50),
    isActive BOOLEAN
);

CREATE TABLE ArtworksTag (
    AWTName VARCHAR(25) PRIMARY KEY
);

CREATE TABLE Workshops (
    WorkshopID INT PRIMARY KEY,
    WSTitle VARCHAR(100),
    WSDate DATE,
    WSPrice DECIMAL(5,2),
    WSLevel VARCHAR(30),
    DurationMinutes INT,
    MaxParticipants INT,
    WSLocation VARCHAR(200),
    WSDescription VARCHAR(400)
);

CREATE TABLE CommunityMember (
    MemberID INT PRIMARY KEY,
    CMName VARCHAR(100),
    CMEmail VARCHAR(100),
    CMCity VARCHAR(100),
    CMBirthyear DATE,
    CMPhone VARCHAR(30),
    MembershipType VARCHAR(10)
);

CREATE TABLE Gallery (
    GalleryID INT PRIMARY KEY,
    GName VARCHAR(50),
    GAddress VARCHAR(50),
    GRating DECIMAL(5,2),
    GOwnerName VARCHAR(50),
    GOpeningHours VARCHAR(100),
    GContactPhone VARCHAR(20),
    GWebsite VARCHAR(100)
);

CREATE TABLE discipline (
    DName VARCHAR(50) PRIMARY KEY
);

CREATE TABLE Booking (
    BookingID INT PRIMARY KEY,
    BookingDate DATE,
    PaymentStatus VARCHAR(9),
    WorkshopID INT,
    MemberID INT,
    FOREIGN KEY (WorkshopID) REFERENCES Workshops(WorkshopID),
    FOREIGN KEY (MemberID) REFERENCES CommunityMember(MemberID)
);

CREATE TABLE Exhibitions (
    ExhibitionID INT PRIMARY KEY,
    EXTitle VARCHAR(100),
    EXStartDate DATE,
    EXTheme VARCHAR(50),
    EXEndDate DATE,
    EXDescription VARCHAR(200),
    EXCuratorName VARCHAR(50),
    GalleryID INT,
    FOREIGN KEY (GalleryID) REFERENCES Gallery(GalleryID)
);

CREATE TABLE Artworks (
    ArtworkID INT PRIMARY KEY,
    AWTitle VARCHAR(100),
    AWType VARCHAR(25),
    AWPrice DECIMAL(9,2),
    AWStatus VARCHAR(9),
    AWCreationYear INT,
    AWMedium VARCHAR(20),
    AWDimensions VARCHAR(20),
    AWDescription VARCHAR(200),
    ExhibitionID INT,
    ArtistID INT,
    FOREIGN KEY (ExhibitionID) REFERENCES Exhibitions(ExhibitionID),
    FOREIGN KEY (ArtistID) REFERENCES Artists(ArtistID)
);

CREATE TABLE Review (
    ReviewID INT PRIMARY KEY,
    Rating DECIMAL(5,2),
    Comment VARCHAR(500),
    ReviewDate DATE,
    ArtworkID INT,
    MemberID INT,
    FOREIGN KEY (ArtworkID) REFERENCES Artworks(ArtworkID),
    FOREIGN KEY (MemberID) REFERENCES CommunityMember(MemberID)
);

CREATE TABLE Has (
    ArtistID INT,
    DName VARCHAR(50),
    FOREIGN KEY (ArtistID) REFERENCES Artists(ArtistID),
    FOREIGN KEY (DName) REFERENCES Discipline(DName),
    PRIMARY KEY (ArtistID, DName)
);

CREATE TABLE Likes (
    MemberID INT,
    DName VARCHAR(50),
    FOREIGN KEY (MemberID) REFERENCES CommunityMember(MemberID),
    FOREIGN KEY (DName) REFERENCES Discipline(DName),
    PRIMARY KEY (MemberID, DName)
);

CREATE TABLE Has_Tag (
    ArtworkID INT,
    AWTName VARCHAR(25),
    FOREIGN KEY (ArtworkID) REFERENCES Artworks(ArtworkID),
    FOREIGN KEY (AWTName) REFERENCES ArtworksTag(AWTName),
    PRIMARY KEY (ArtworkID, AWTName)
);

CREATE TABLE Participates_in (
    ArtistID INT,
    WorkshopID INT,
    FOREIGN KEY (ArtistID) REFERENCES Artists(ArtistID),
    FOREIGN KEY (WorkshopID) REFERENCES Workshops(WorkshopID),
    PRIMARY KEY (ArtistID, WorkshopID)
);

