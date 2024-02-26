CREATE TABLE public.device_languages
(
    value        INTEGER,
    device_model VARCHAR(255) NOT NULL,
    language     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_device_languages PRIMARY KEY (device_model, language)
);
