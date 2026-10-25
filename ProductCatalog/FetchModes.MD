findById results
FetchType        FetchMode          Result
LAZY             SELECT             Asked For Products - Queries - 2 select queries
                                    Not Asked For Products - Queries - 1 select query
NPlusOne                            Asked For Products - Queries - 5 select queries 1 for each row

EAGER            SELECT             Asked For Products - Queries - 2 select
                                    Not Asked For Products - Queries - 2 select

LAZY             JOIN               Asked For Products - Queries - 1 join
                                    Not Asked For Products - Queries - 1 join

findByName will give below results different
LAZY             JOIN               Asked For Products - Queries - 2 select queries
                                    Not Asked For Products - Queries - 1 select

EAGER            JOIN               Asked For Products - Queries - 1 join
                                    Not Asked For Products - Queries - 1 join

findByName will give below results different
EAGER            JOIN               Asked For Products - Queries - 2 select queries
                                    Not Asked For Products - Queries - 2 select queries

LAZY             SUBSELECT          Asked For Products - Queries - 2 select
                                    Not Asked For Products - Queries - 1 select
NPlusOne                            Asked For Products - Queries - 2 select queries 1 for each table

EAGER            SUBSELECT          Asked For Products - Queries - 2 select
                                    Not Asked For Products - Queries - 2 select


