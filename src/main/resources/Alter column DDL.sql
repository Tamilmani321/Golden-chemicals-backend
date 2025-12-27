ALTER TABLE gmd.transaction DROP COLUMN products;
ALTER TABLE gmd.transaction CHANGE product products VARCHAR(255);