/*Exercise 3: Stored Procedures

Scenario 1: The bank needs to process monthly interest for all savings accounts.
Question: Write a stored procedure ProcessMonthlyInterest that calculates and updates the balance of all savings accounts by applying an interest rate of 1% to the current balance.
*/

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest AS
BEGIN
    -- Apply 1% interest to all savings accounts
    UPDATE Accounts
    SET Balance = Balance + (Balance * 0.01)
    WHERE AccountType = 'Savings';

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('1% interest applied to all savings accounts.');
END;
/

SET SERVEROUTPUT ON;
EXEC ProcessMonthlyInterest;


--Scenario 2: The bank wants to implement a bonus scheme for employees based on their performance.
--Question: Write a stored procedure UpdateEmployeeBonus that updates the salary of employees in a given department by adding a bonus percentage passed as a parameter.

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus(
    p_department IN VARCHAR2,
    p_bonus_percent IN NUMBER
) AS
BEGIN
    UPDATE Employees
    SET Salary = Salary + (Salary * (p_bonus_percent / 100))
    WHERE Department = p_department;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Bonus of ' || p_bonus_percent || '% applied to department: ' || p_department);
END;
/

SET SERVEROUTPUT ON;
EXEC UpdateEmployeeBonus('IT', 10);
-- Adds 10% bonus to all employees in IT department


--Scenario 3: Customers should be able to transfer funds between their accounts.
--Question: Write a stored procedure TransferFunds that transfers a specified amount from one account to another, checking that the source account has sufficient balance before making the transfer.

CREATE OR REPLACE PROCEDURE TransferFunds(
    p_from_account_id IN NUMBER,
    p_to_account_id   IN NUMBER,
    p_amount          IN NUMBER
) AS
    v_from_balance NUMBER;
BEGIN
    -- Get current balance of source account
    SELECT Balance INTO v_from_balance
    FROM Accounts
    WHERE AccountID = p_from_account_id
    FOR UPDATE;

    -- Check if sufficient funds
    IF v_from_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20001, 'Insufficient funds in source account.');
    END IF;

    -- Deduct from source
    UPDATE Accounts
    SET Balance = Balance - p_amount
    WHERE AccountID = p_from_account_id;

    -- Add to destination
    UPDATE Accounts
    SET Balance = Balance + p_amount
    WHERE AccountID = p_to_account_id;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Transfer of Rs ' || p_amount || ' from Account ' ||
                         p_from_account_id || ' to Account ' || p_to_account_id || ' completed.');
END;
/

SET SERVEROUTPUT ON;
EXEC TransferFunds(1, 2, 500);
-- Transfers Rs 500 from account 1 to account 2


