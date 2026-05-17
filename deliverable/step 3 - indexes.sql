USE ProjectDB;

CREATE INDEX idx_artworks_status ON Artworks(AWstatus); 

CREATE INDEX idx_booking_workshopid ON Booking(WorkshopID); 
 
CREATE INDEX idx_review_artworkid ON Review(ArtworkID); 

