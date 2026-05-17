USE ProjectDB;

CREATE VIEW vw_artist_public AS 
SELECT 
    Aname, 
    Acity, 
    Abio, 
    Awebsite, 
    AsocialMedia, 
    isActive 
FROM Artists; 


 

CREATE VIEW vw_member_public AS 
SELECT 
    MemberID, 
    CMname, 
    CMcity, 
    MembershipType 
FROM CommunityMember; 

 
CREATE VIEW vw_available_artworks AS 
SELECT 
    aw.ArtworkID, 
    aw.AWtitle, 
    aw.AWtype, 
    aw.AWprice, 
    aw.AWmedium, 
    aw.AWdimensions, 
    ar.Aname        AS ArtistName, 
    ar.Acity        AS ArtistCity 
FROM Artworks aw 
JOIN Artists ar ON aw.ArtistID = ar.ArtistID 
WHERE aw.AWstatus = 'Available'; 