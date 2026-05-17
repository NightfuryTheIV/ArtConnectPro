USE ProjectDB;
DROP PROCEDURE IF EXISTS sp_purchase_and_book;

DELIMITER //

CREATE PROCEDURE sp_purchase_and_book(
    IN p_memberID    INT,
    IN p_artworkID   INT,
    IN p_workshopID  INT,
    IN p_bookingDate DATE
)
BEGIN
    -- ─── Declare variables ───────────────────────────────────────────
    DECLARE v_status        VARCHAR(9);
    DECLARE v_currentCount  INT;
    DECLARE v_maxPart       INT;
    DECLARE v_wsDate        DATE;
    DECLARE v_artistID      INT;
    DECLARE v_alreadyBooked INT;

    -- Declare EXIT HANDLER: if anything goes wrong, rollback everything
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Transaction failed. All changes have been rolled back.';
    END;

    -- ─── Start transaction ───────────────────────────────────────────
    START TRANSACTION;

    -- ─── Step 1: Check artwork is available ──────────────────────────
    SELECT AWstatus, ArtistID
    INTO v_status, v_artistID
    FROM Artworks
    WHERE ArtworkID = p_artworkID
    FOR UPDATE;  -- Lock the row to prevent concurrent purchases

    IF v_status <> 'Available' THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Artwork is not available for purchase.';
    END IF;

    -- ─── Step 2: Check workshop capacity ─────────────────────────────
    SELECT WSdate, MaxParticipants
    INTO v_wsDate, v_maxPart
    FROM Workshops
    WHERE WorkshopID = p_workshopID
    FOR UPDATE;  -- Lock to prevent race condition on capacity

    SELECT COUNT(*)
    INTO v_currentCount
    FROM Booking
    WHERE WorkshopID = p_workshopID;

    IF v_currentCount >= v_maxPart THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Workshop is fully booked.';
    END IF;

    -- ─── Step 3: Check booking date is valid ─────────────────────────
    IF p_bookingDate > v_wsDate THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Booking date cannot be after the workshop date.';
    END IF;

    -- ─── Step 4: Check member hasn't already booked this workshop ─────
    SELECT COUNT(*)
    INTO v_alreadyBooked
    FROM Booking
    WHERE WorkshopID = p_workshopID
      AND MemberID   = p_memberID;

    IF v_alreadyBooked > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Member is already booked for this workshop.';
    END IF;

    -- ─── Step 5: Mark artwork as Sold ────────────────────────────────
    UPDATE Artworks
    SET AWstatus = 'Sold'
    WHERE ArtworkID = p_artworkID;

    -- ─── Step 6: Create the workshop booking ─────────────────────────
    INSERT INTO Booking (BookingDate, PaymentStatus, WorkshopID, MemberID)
    VALUES (p_bookingDate, 'Pending', p_workshopID, p_memberID);

    -- ─── Step 7: Verify the workshop's instructor is the artwork artist
    --            and register them as participant if not already registered
    IF NOT EXISTS (
        SELECT 1 FROM Participates_in
        WHERE ArtistID   = v_artistID
          AND WorkshopID = p_workshopID
    ) THEN
        INSERT INTO Participates_in (ArtistID, WorkshopID)
        VALUES (v_artistID, p_workshopID);
    END IF;

    -- ─── All good: commit ─────────────────────────────────────────────
    COMMIT;

    SELECT 'Transaction successful.' AS Result,
            p_artworkID              AS PurchasedArtwork,
            p_workshopID             AS BookedWorkshop,
            p_memberID               AS MemberID;

END;
//
DELIMITER ;

CALL sp_purchase_and_book(4, 1, 1, '2024-05-01');

