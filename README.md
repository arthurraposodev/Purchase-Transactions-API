When H2 database is not closed correctly, the ID sequence might jump many entries on next object creation, due to an identity cache of up to 32 entries being cached. This jump is to avoid potential collisions.

Precision is set to 15 digits to accomodate for up to 1 trillion dollar operations.

Exchange rate history is saved from the first quarter available going back 10 years from the start of the database and is checked monthly for new entries (currently, the Treasure Department updates their dataset quarterly).

No ID is accepted on input because client defining own ID is a security concern.

85% Test Line Coverage