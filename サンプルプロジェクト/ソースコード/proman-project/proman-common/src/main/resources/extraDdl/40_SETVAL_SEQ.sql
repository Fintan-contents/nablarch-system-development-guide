CREATE SEQUENCE IF NOT EXISTS public.sq_organization_id INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 OWNED BY organization.organization_id;
ALTER SEQUENCE public.sq_organization_id OWNER TO postgres;
ALTER TABLE ONLY organization ALTER COLUMN organization_id SET DEFAULT nextval('public.sq_organization_id'::regclass);

CREATE SEQUENCE IF NOT EXISTS public.sq_project_id INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 OWNED BY project.project_id;
ALTER SEQUENCE public.sq_project_id OWNER TO postgres;
ALTER TABLE ONLY project ALTER COLUMN project_id SET DEFAULT nextval('public.sq_project_id'::regclass);

CREATE SEQUENCE IF NOT EXISTS public.sq_user_id INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 OWNED BY users.user_id;
ALTER SEQUENCE public.sq_user_id OWNER TO postgres;
ALTER TABLE ONLY users ALTER COLUMN user_id SET DEFAULT nextval('public.sq_user_id'::regclass);

-- project_id列で使用するシーケンスについて、GSPで投入したデータと値が重複しないように値を設定する。
ALTER SEQUENCE public.sq_project_id RESTART WITH 201;
