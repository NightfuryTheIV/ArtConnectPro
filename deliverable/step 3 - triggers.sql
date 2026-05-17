USE ProjectDB;

DELIMITER // 
CREATE TRIGGER check_booking_date BEFORE INSERT ON Booking FOR EACH ROW 
BEGIN 
    DECLARE ws_date DATE; 
    SELECT WSdate INTO ws_date FROM Workshops WHERE WorkshopID = NEW.WorkshopID; 
    IF NEW.BookingDate > ws_date THEN 
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Booking date cannot be after the workshop date.'; 
    END IF; 
END; 
// 
DELIMITER ; 



DELIMITER // 
CREATE TRIGGER trg_check_capacity BEFORE INSERT ON Booking FOR EACH ROW 
BEGIN 
    DECLARE current_count INT; 
    DECLARE max_cap INT; 
 
    SELECT COUNT(*) INTO current_count FROM Booking WHERE WorkshopID = NEW.WorkshopID; 
 
    SELECT MaxParticipants INTO max_cap FROM Workshops WHERE WorkshopID = NEW.WorkshopID; 
 
    IF current_count >= max_cap THEN 
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Workshop is fully booked. No seats available.'; 
    END IF; 
END; 
// 
DELIMITER ; 

 
DELIMITER // 
CREATE TRIGGER trg_audit_exhibition_update 
AFTER UPDATE ON Exhibitions 
FOR EACH ROW 
BEGIN 
    IF OLD.EXtitle <> NEW.EXtitle THEN 
        INSERT INTO ExhibitionAuditLog (ExhibitionID, ChangedField, OldValue, NewValue) 
        VALUES (OLD.ExhibitionID, 'EXtitle', OLD.EXtitle, NEW.EXtitle); 
    END IF; 
    IF OLD.EXstartDate <> NEW.EXstartDate THEN 
        INSERT INTO ExhibitionAuditLog (ExhibitionID, ChangedField, OldValue, NewValue) 
        VALUES (OLD.ExhibitionID, 'EXstartDate', OLD.EXstartDate, NEW.EXstartDate); 
    END IF; 
    IF OLD.EXendDate <> NEW.EXendDate THEN 
        INSERT INTO ExhibitionAuditLog (ExhibitionID, ChangedField, OldValue, NewValue) 
        VALUES (OLD.ExhibitionID, 'EXendDate', OLD.EXendDate, NEW.EXendDate); 
    END IF; 
END; 
// 
DELIMITER ; 