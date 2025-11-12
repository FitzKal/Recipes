ALTER TABLE recipes
DROP CONSTRAINT recipes_category_check;

ALTER TABLE recipes
    ADD CONSTRAINT recipes_category_check
        CHECK (category IN ('DESSERT', 'SOUP', 'MAIN', 'DRINK'));