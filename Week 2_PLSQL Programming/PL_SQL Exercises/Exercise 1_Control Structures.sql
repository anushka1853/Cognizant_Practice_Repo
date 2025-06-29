--This file contains codes for all three scenerios of Exercise 1: Control Structures

/* Exercise 1: Control Structures

Scenario 1: The bank wants to apply a discount to loan interest rates for customers above 60 years old.
Question: Write a PL/SQL block that loops through all customers, checks their age, and if they are above 60, apply a 1% discount to their current loan interest rates.
*/

-- Scenerio 1 solution:

CREATE OR REPLACE PROCEDURE ApplySeniorCitizenInterestDiscount AS
BEGIN
    UPDATE Loans
    SET InterestRate = InterestRate - 1
    WHERE CustomerID IN (
        SELECT C.CustomerID
        FROM Customers C
        WHERE Loans.CustomerID = C.CustomerID
        AND MONTHS_BETWEEN(SYSDATE, C.DOB) / 12 > 60
    );

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('1% discount applied to interest rates of customers above 60.');
END;
/

SET SERVEROUTPUT ON;

BEGIN
    Execute ApplySeniorCitizenInterestDiscount;
END;
/


/*Scenario 2: A customer can be promoted to VIP status based on their balance.
Question: Write a PL/SQL block that iterates through all customers and sets a flag IsVIP to TRUE for those with a balance over $10,000. */

ALTER TABLE Customers
ADD IsVIP CHAR(1);  -- We'll use 'Y' for TRUE, 'N' for FALSE

CREATE OR REPLACE PROCEDURE PromoteVIPCustomers AS
BEGIN
    -- Set IsVIP to 'Y' for customers with balance > 10000
    UPDATE Customers
    SET IsVIP = 'Y'
    WHERE Balance > 10000;

    -- Optionally, set IsVIP to 'N' for others
    UPDATE Customers
    SET IsVIP = 'N'
    WHERE Balance <= 10000;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('VIP status updated for eligible customers.');
END;
/

-- Enable output (only needed in tools like SQL*Plus or SQL Developer)
SET SERVEROUTPUT ON;

-- Execute the procedure
BEGIN
    PromoteVIPCustomers;
END;
/

--Scenario 3: The bank wants to send reminders to customers whose loans are due within the next 30 days.
--Question: Write a PL/SQL block that fetches all loans due in the next 30 days and prints a reminder message for each customer.

CREATE OR REPLACE PROCEDURE SendLoanDueReminders AS
    -- Variable declarations
    v_customer_id    NUMBER(20);
    v_loan_id        NUMBER(20);
    v_end_date       DATE;
    v_customer_name  VARCHAR2(100);
BEGIN
    -- Loop through loans due in the next 30 days
    FOR rec IN (
        SELECT L.LoanID, L.EndDate, C.CustomerID, C.Name AS CustomerName
        FROM Loans L
        JOIN Customers C ON L.CustomerID = C.CustomerID
        WHERE L.EndDate BETWEEN SYSDATE AND SYSDATE + 30
    ) LOOP
        -- Assign values to variables
        v_loan_id := rec.LoanID;
        v_end_date := rec.EndDate;
        v_customer_id := rec.CustomerID;
        v_customer_name := rec.CustomerName;

        -- Print the reminder
        DBMS_OUTPUT.PUT_LINE(
            'Reminder: Dear ' || v_customer_name ||
            ', your loan (Loan ID: ' || v_loan_id ||
            ') is due on ' || TO_CHAR(v_end_date, 'DD-Mon-YYYY') ||
            '. Please ensure timely payment.'
        );
    END LOOP;
END;
/

SET SERVEROUTPUT ON;

EXEC SendLoanDueReminders;





