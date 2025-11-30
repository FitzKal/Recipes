--
-- PostgreSQL database dump
--

\restrict EDKkgmzSvjWBVZbbUXJL6pqZupUANdkpmFDUPxgnlRqDCta73B1ADKV2EJwPfFU

-- Dumped from database version 15.15 (Debian 15.15-1.pgdg13+1)
-- Dumped by pg_dump version 15.15 (Debian 15.15-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE ONLY public.recipes DROP CONSTRAINT fklc3x6yty3xsupx80hqbj9ayos;
DROP INDEX public.flyway_schema_history_s_idx;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
ALTER TABLE ONLY public.users DROP CONSTRAINT ukr43af9ap4edm43mmtq01oddj6;
ALTER TABLE ONLY public.recipes DROP CONSTRAINT recipes_pkey;
ALTER TABLE ONLY public.flyway_schema_history DROP CONSTRAINT flyway_schema_history_pk;
DROP SEQUENCE public.users_seq;
DROP TABLE public.users;
DROP SEQUENCE public.recipes_seq;
DROP TABLE public.recipes;
DROP TABLE public.flyway_schema_history;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- Name: recipes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recipes (
    id bigint NOT NULL,
    category character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    ingredients character varying(200000),
    instructions character varying(200000),
    picture_src character varying(255) NOT NULL,
    recipe_title character varying(255) NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT recipes_category_check CHECK (((category)::text = ANY ((ARRAY['DESSERT'::character varying, 'SOUP'::character varying, 'MAIN'::character varying, 'DRINK'::character varying])::text[])))
);


ALTER TABLE public.recipes OWNER TO postgres;

--
-- Name: recipes_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recipes_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recipes_seq OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    password character varying(255),
    role character varying(255),
    username character varying(255) NOT NULL,
    CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['USER'::character varying, 'ADMIN'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_seq OWNER TO postgres;

--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
\.


--
-- Data for Name: recipes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recipes (id, category, description, ingredients, instructions, picture_src, recipe_title, user_id) FROM stdin;
1	DESSERT	A moist and fluffy carrot cake filled with grated carrots, chopped nuts, and topped with a smooth cream cheese frosting.	300g flour, 2 tsp baking powder, 1 tsp baking soda, 1 tsp salt, 1 tsp ground cinnamon, 0.5 tsp ground nutmeg, 320ml vegetable oil, 220g light brown sugar, 200g granulated sugar, 4 large eggs, 10ml vanilla extract, 315g grated carrots, 120g chopped walnuts or pecans, 227g cream cheese, 227g unsalted butter, 5ml vanilla extract, 480-600g powdered sugar	Preheat oven to 180°C. In a bowl, mix flour, baking powder, baking soda, salt, cinnamon, and nutmeg. In another bowl, whisk together the oil, brown sugar, and granulated sugar, then add eggs and vanilla. Combine wet and dry ingredients, then fold in grated carrots and nuts. Pour batter into greased cake pans and bake for 35–40 minutes. Let cool on a wire rack. For the frosting, beat together the butter, cream cheese, vanilla, and powdered sugar until smooth, then spread over the cooled cake.	https://preppykitchen.com/wp-content/uploads/2024/02/Carrot-Cake-Feature.jpg	Carrot and Walnut Cake	1
2	MAIN	A quick and easy pizza dough perfect for homemade pizzas, with a soft yet chewy crust and minimal ingredients.	950g bread flour, 50g semolina flour, 4g active dry yeast, 750g water, 24g salt	In a large bowl combine bread flour and semolina flour. Dissolve the yeast in the water, then add to the flour mixture along with the salt and mix until a sticky dough forms. Knead briefly until smooth, place dough in an oiled bowl, cover and let rise at room temperature until doubled in size. Then shape into pizza crust, add toppings and bake at high temperature until golden.	https://i2.wp.com/natashasbaking.com/wp-content/uploads/2024/06/Pizza_in_3_hours_2620-2.jpg?strip=info&w=1500&ssl=1	Pizza Dough	1
3	SOUP	A rich and velvety broccoli soup made with fresh florets, aromatic onions and garlic, blended to a smooth consistency.	700 g broccoli florets, 1 large onion chopped, 3 cloves garlic minced, 1 liter vegetable stock or 2-3 bouillon cubes, 250 ml milk or cream, 30 g butter, salt and pepper to taste	Melt butter in a large pot and sauté onion and garlic for a few minutes until softened. Add broccoli and vegetable stock, bring to a simmer and cook until broccoli is tender. Use a blender or immersion blender to puree the soup until smooth. Stir in roux and milk or cream, season with salt and pepper, then reheat gently. Serve hot, with bacon and soup pearls.	https://cookingwithcoit.com/wp-content/uploads/2021/05/HERO_Cream-of-Broccoli-Soup.jpg	Creamy Broccoli Soup	1
4	MAIN	A classic Caesar salad made with crisp romaine lettuce, creamy dressing, and crunchy homemade croutons.	2 medium romaine lettuce hearts, 100g parmesan cheese, 150g bread cubes, 60ml olive oil, 2 garlic cloves minced, 2 egg yolks, 30ml lemon juice, 10ml Dijon mustard, 30g anchovies, salt and pepper to taste	Preheat oven to 180°C. Toss bread cubes with olive oil and minced garlic, bake for 10–12 minutes until golden. In a bowl, whisk egg yolks, lemon juice, mustard, and mashed anchovies. Slowly drizzle in olive oil while whisking to form an emulsion. Add grated parmesan and season. Toss chopped romaine lettuce with dressing and top with croutons and shaved parmesan.	https://preppykitchen.com/wp-content/uploads/2025/06/caesar-salad-feature.jpg	Caesar Salad	1
5	DESSERT	Soft and moist banana bread filled with melty chocolate chips — perfect for breakfast or a sweet snack.	250g ripe bananas (about 3 medium), 100g melted butter, 150g brown sugar, 2 eggs, 5ml vanilla extract, 190g all-purpose flour, 5g baking soda, 2g salt, 150g chocolate chips	Preheat oven to 175°C and line a loaf pan with parchment paper. In a bowl, mash bananas and mix with melted butter and brown sugar. Add eggs and vanilla, stir until smooth. In another bowl, mix flour, baking soda, and salt. Combine wet and dry ingredients, then fold in chocolate chips. Pour into loaf pan and bake for 50–60 minutes until a toothpick comes out clean. Cool before slicing.	https://preppykitchen.com/wp-content/uploads/2019/10/Chocolate-Chip-Banana-Bread-Feature.jpg	Chocolate Chip Banana Bread	1
6	DESSERT	Soft and fluffy cinnamon rolls filled with Biscoff spread, brown sugar, and cinnamon, topped with a rich cream cheese frosting.	180g warm milk, 7g instant yeast, 50g sugar, 1 egg + 1 egg yolk, 57g melted butter, 360–420g flour, 3g salt, 76g softened butter, 160-200g Biscoff spread, 165g brown sugar, 8g cinnamon, crushed Biscoff cookies, 170g cream cheese, 57g unsalted butter, 120g powdered sugar, 3ml vanilla extract, optional 80–120ml warm cream	Combine warm milk and yeast. Add sugar, eggs, and melted butter, then mix. Stir in salt and flour to form a dough. Knead for 8–10 minutes and let rise for 1–1.5 hours. Mix softened butter, Biscoff spread, brown sugar, and cinnamon for the filling. Roll dough into a 23×35 cm rectangle, spread filling evenly, and sprinkle crushed Biscoff cookies. Slice into 8 pieces, place in a greased 23×33 cm pan, cover, and rise for 35–45 minutes. Pour warm cream over before baking. Bake at 175°C for 22–28 minutes until golden. For the icing, beat cream cheese, butter, and Biscoff spread until smooth, then add powdered sugar and vanilla. Spread over warm rolls and sprinkle crushed cookies. Enjoy warm!	https://eatsdelightful.com/wp-content/uploads/2024/04/iced-biscoff-cinnamon-rolls-in-baking-dish-7-scaled.jpg	Biscoff Cinnamon Rolls	1
7	MAIN	A rich and creamy North Indian chicken curry made with butter, cream, tomatoes, and aromatic spices.	300g boneless chicken breast, 15g ginger garlic paste, 10g red chili powder, salt to taste, oil for pan frying, 500g tomatoes (roughly chopped) OR tomato puree/paste, 100g onions (roughly chopped), 10g garlic paste, 50g cashew nuts, 1g kasoori methi, 2g garam masala, 20g sugar, 6g Kashmiri chili powder, 70g butter, 45ml cream, 20ml malt vinegar or 15ml white vinegar, salt to taste	Marinate the chicken with ginger garlic paste, red chili powder, and salt for 15–20 minutes. Heat oil in a pan and fry the marinated chicken pieces until golden; set aside. In the same pan, add onion, a little oil, and a spoonful of butter. Once onions are cooked, add chopped tomatoes and cashews. Add water, garlic paste, salt, vinegar, sugar, garam masala, and chili powder. Mix well and simmer for 15–20 minutes. Blend into a smooth puree, then strain back into the pan. Add butter, cream, chicken, and kasoori methi. Simmer for 5–7 minutes. Garnish with cream and kasoori methi before serving.	https://nickskitchen.com/wp-content/uploads/2025/08/NK_Butter-Ckn_1-scaled.jpg	Butter Chicken	1
8	DRINK	A refreshing homemade lemonade made with lemon zest syrup and fresh lemon juice.	4 lemons, zest of 4 lemons, 200g sugar, 500ml water (hot, about 65°C), extra water or sparkling water to serve	Zest the thin yellow skin of the lemons, avoiding the white pith. Save the zested lemons. Measure the zest and combine with twice as much sugar (by volume). Mix and muddle together, then cover and let rest at room temperature for 4 hours. Heat water to about 65°C and combine it with the lemon-sugar mixture to make a lemon syrup. Mix and strain into a jar. Juice the zested lemons. To serve, combine lemon juice, syrup, and cold water or sparkling water to taste. Serve chilled with ice, and some mint.	https://oliveoilsfromspain.org/wp-content/uploads/2021/03/healthy-lemonade.jpg	Homemade Lemonade	1
9	SOUP	A rustic Italian-style garlic soup made with roasted garlic, olive oil, bread, and Parmesan - comforting and full of flavor.	2 whole garlic bulbs, 30ml olive oil, 30g butter, 1 onion (sliced), 1.2L chicken stock or vegetable stock, 2 sprigs fresh thyme, 2 bay leaves, 2 egg yolks, 30g grated Parmesan cheese, 2 slices crusty bread (toasted), salt and black pepper to taste	Preheat the oven to 180°C. Slice the tops off the garlic bulbs, drizzle with olive oil, wrap in foil, and roast for 40–45 minutes until soft. In a pot, melt butter and sauté sliced onion until translucent. Add the roasted garlic (squeezed from the skins), thyme, bay leaves, and stock. Simmer for 15 minutes, then remove herbs and blend the soup until smooth. In a bowl, whisk egg yolks with Parmesan and a ladle of hot soup, then slowly mix back into the pot while stirring. Season with salt and pepper. Serve hot with toasted bread and a drizzle of olive oil.	https://recipe30.com/wp-content/uploads/2019/11/Garlic-soup.jpg	Italian Garlic Soup	1
10	DESSERT	Soft, fluffy Hungarian-style chocolate rolls filled with rich cocoa, sugar, and dark chocolate - a family favorite that’s simple yet irresistibly delicious.	600g all-purpose flour, 300ml lukewarm milk, 30g fresh yeast, 80g sugar, a pinch of salt, 10g vanilla sugar or seeds from 1 vanilla pod, 50g soft butter. For the filling: 100g melted butter, 100g Dutch-process cocoa powder, 150g sugar, 150g dark chocolate (roughly chopped or chocolate chips). For brushing: 1 whole egg + 1 egg yolk (beaten).	In a bowl, dissolve the yeast and sugar in lukewarm milk. Add flour, salt, vanilla, and soft butter, and knead into a smooth dough. If the dough feels dry, add 30–50ml more milk depending on flour strength. Let it rise for 45–60 minutes until doubled. For the filling, mix melted butter, cocoa powder, and sugar into a thick paste. Roll out the dough into a rectangle, spread the filling evenly, and sprinkle with chopped dark chocolate. Roll tightly, then cut into 25 even rolls. Place on a baking tray lined with parchment paper. Brush with the egg mixture. Bake in a preheated oven at 200°C (top and bottom heat) for 12–15 minutes, until golden brown. Let cool slightly before serving warm.	https://i.ytimg.com/vi/clL6uYEse2E/maxresdefault.jpg	Chocolate Swirl Rolls	53
11	MAIN	A classic Italian pasta dish with pancetta, eggs, cheese, and black pepper, creating a creamy, silky sauce without any cream.	450g spaghetti, 225g pancetta or thick-cut bacon (diced), 15ml olive oil, 2 garlic cloves minced (optional), 3 large eggs, 100g grated Parmesan or Pecorino cheese, salt to taste, black pepper to taste	Bring a large pot of salted water to a boil. Heat olive oil in a large pan over medium heat, add pancetta or bacon and cook until crisp. Add garlic if using, cook briefly, then remove from heat. In a bowl beat the eggs and mix with half of the cheese. Cook the spaghetti until al dente, then transfer it hot into the bowl with pancetta. Immediately add the egg mixture and toss quickly until creamy, adding a little pasta water if needed. Season with pepper and salt, and serve topped with the remaining cheese.	https://upload.wikimedia.org/wikipedia/commons/3/33/Espaguetis_carbonara.jpg	Spaghetti alla Carbonara	53
12	DESSERT	A creamy and light classic Italian tiramisu with coffee-soaked ladyfingers and mascarpone cream, letting the coffee flavour shine without being overly sweet.	450 g mascarpone cheese (cold), 4 egg yolks, 133 g granulated sugar, 1 tsp vanilla extract, 1/4 tsp salt, 360 g heavy cream (chilled) OR 4 egg whites, approx. 30-36 ladyfingers, 360 g strong black coffee (room temperature), 2 tbsp (~16 g) cocoa powder for dusting	Whisk the mascarpone until creamy. In a heat-proof bowl over simmering water (double boiler) whisk egg yolks + sugar for 2 minutes until light and fluffy. Pour into mascarpone along with vanilla and salt. Whip the heavy cream until medium stiff peaks (or use beaten egg whites) and fold into the mascarpone mixture in 2-3 additions. Pour the strong coffee into a wide bowl; dip each ladyfinger ~1-2 seconds per side and layer in the bottom of your dish. Spread half the mascarpone cream mixture over the first layer of dipped ladyfingers. Repeat with another layer of ladyfingers, then the remaining cream. Cover and refrigerate for at least 6 hours or ideally overnight. Dust cocoa powder evenly on top right before serving.	https://bakewithzoha.com/wp-content/uploads/2025/06/tiramisu-5.jpg	Classic Italian Tiramisu	53
13	MAIN	A crispy, cheesy smash-style burger made with thin 100g beef patties, caramelized onions, toasted brioche buns, and your choice of fresh toppings and sauces.	400g ground beef (about 20% fat), 2 medium red onions thinly sliced, 4 brioche burger buns, 200–250g cheddar cheese (about 50–60g per patty), 8 slices bacon, 2 tomatoes sliced, 1 cucumber or pickles sliced, a handful of lettuce leaves, salt, black pepper, garlic powder, sugar (for caramelizing onions), optional toppings: extra pickles, sautéed mushrooms, extra sauces, optional sauces: mayonnaise, garlic mayo, ketchup, mustard, burger sauce	Cook the bacon in a pan until crispy, then set aside. Use the bacon fat to cook the thinly sliced red onions with a pinch of salt and sugar until soft and caramelized, then remove from the pan. Divide the beef into four loose 100g balls without packing them tightly. Heat the pan on high and place one beef ball inside. Add a spoonful of caramelized onions on top, season with salt, pepper, and garlic powder, then smash the beef flat with a spatula or small pot. Cook for 1.5–2 minutes, flip it so the onions are underneath, add 50–60g cheddar cheese on top, pour a splash of water into the pan, and cover with a lid to melt the cheese. Repeat for all patties. Toast the brioche buns in the same pan until golden. Assemble the burgers: spread your preferred sauces on the buns, add lettuce, tomato slices, cucumber or pickles, place the cheesy smash patty on top, then add bacon. Serve warm with fries.	https://cdn.apartmenttherapy.info/image/upload/f_jpg,q_auto:eco,c_fill,g_auto,w_1500,ar_16:9/tk%2Fphoto%2F2025%2F05-2025%2F2025-05-oklahoma-onion-burger%2Foklahoma-onion-burger-0376_1	Smash Burger	53
15	MAIN	A traditional slow-cooked Italian bolognese sauce made with beef, soffritto (onion, carrot, celery), tomato, wine, and milk, simmered for depth and richness.	500g ground beef (or 300g beef + 200g pork mince), 1 medium onion finely diced, 1 medium carrot finely diced, 1 celery stalk finely diced, 2 garlic cloves minced, 150ml dry white wine or red wine, 700g tomato passata or crushed tomatoes, 2 tbsp tomato paste, 250–350ml beef stock, 150ml whole milk, 2 tbsp olive oil, 1 tsp salt, black pepper to taste, 1 parmesan rind (optional), 350g spaghetti or tagliatelle, grated Parmesan for serving	Heat olive oil in a large pot over medium heat. Add the finely diced onion, carrot, and celery and cook for 7–10 minutes until soft. Add garlic and cook briefly. Add the ground beef and cook until browned, breaking it up as it cooks. Pour in the wine and let it simmer until mostly evaporated. Stir in tomato paste, then add tomato passata, beef stock, and the optional parmesan rind. Season with salt and pepper. Reduce the heat to low and let the sauce simmer uncovered for 1.5–2 hours, stirring occasionally; add small splashes of stock or water if it becomes too thick. In the last 20 minutes, stir in the milk and continue cooking gently. Remove the parmesan rind before serving. Cook the pasta in salted water until al dente, drain, and serve topped with the bolognese sauce and grated Parmesan.	https://happilyunprocessed.com/wp-content/uploads/2019/03/Bolognese-Sauce-2a.jpg	Classic Bolognese	52
16	DESSERT	Thin, soft Hungarian-style crepes made from a light batter of flour, milk, sparkling water and eggs. Perfect for sweet or savoury fillings.	200g flour, 2 eggs, 300ml milk, 200ml sparkling water, 1 pinch salt, 75ml oil	Whisk together the flour, eggs, milk, sparkling water, salt, and oil until the batter is smooth and lump-free. Let the batter rest for 10–20 minutes. Heat a lightly oiled pan over medium heat. Before cooking the first crêpe, add a small amount of oil to the hot pan. Pour a thin layer of batter into the pan, tilt to spread evenly, and cook until the edges lift and the bottom is lightly golden. Flip and cook the other side briefly. Repeat with the remaining batter. Fill with your preferred sweet or savoury fillings and roll or fold.	https://palacsintavilag.hu/wp-content/uploads/2024/09/horvath-ilona-palacsinta-recept.jpg.webp	Hungarian Crepes (Palacsinta)	52
14	MAIN	A rich, thick and flavourful spaghetti bolognese sauce made from ground beef, tomato passata, herbs and garlic, served over spaghetti.	500g ground beef (beef mince), 1 onion finely chopped, 2 garlic cloves minced, 700g tomato passata (tomato puree), about 750mL beef stock, 2 tsp dried Italian herb mix, 1/4-1/2 tsp chilli flakes (optional), 2 tbsp tomato paste, 2 tsp Worcestershire sauce, 350g spaghetti (uncooked), salt and black pepper to taste, grated Parmesan for serving	Heat a large pot over medium heat, add onion and garlic and cook until translucent. Add ground beef and cook until browned. Pour in tomato passata and beef stock, then stir in herb mix, chilli flakes, tomato paste, Worcestershire sauce, salt and pepper. Bring to a simmer, reduce heat and cook, stirring occasionally, until sauce thickens and flavours meld (about 30-45 minutes). Meanwhile cook the spaghetti according to packet instructions until al dente, drain and reserve a little pasta water. Toss the spaghetti into the sauce or serve the sauce over the spaghetti. Serve immediately topped with grated Parmesan.	https://www.recipetineats.com/tachyon/2018/07/Spaghetti-Bolognese.jpg?brid=yG2Fc9NvGM_QDOrBaZxbRw	Spaghetti Bolognese	52
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, password, role, username) FROM stdin;
1	$2a$10$GQ6oGrEgfZuY..o/qgtbju7gxOcfkSRO0qUIme3Lt9sv9B063dM6K	USER	Bence
2	$2a$10$jLqcRKCTL9anitVdIeg5neVqq0QfivzwxvbDN2cGgpTePxAeQzoIG	ADMIN	Admin
52	$2a$10$JJYCpJ/wznrIVOL8DVcT.efQsOVFo1Ia.6swTTsYOrx1FjsbDyl2.	USER	Gerzson
53	$2a$10$AnVogYh6IZjH/Jd0uFkF/.ayLwSTRFc.Fuay8tWbHLthbA2CED6lu	USER	Veronika
\.


--
-- Name: recipes_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recipes_seq', 51, true);


--
-- Name: users_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_seq', 101, true);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: recipes recipes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT recipes_pkey PRIMARY KEY (id);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: recipes fklc3x6yty3xsupx80hqbj9ayos; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT fklc3x6yty3xsupx80hqbj9ayos FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- PostgreSQL database dump complete
--

\unrestrict EDKkgmzSvjWBVZbbUXJL6pqZupUANdkpmFDUPxgnlRqDCta73B1ADKV2EJwPfFU

