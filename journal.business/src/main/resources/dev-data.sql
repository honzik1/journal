INSERT INTO USER (ID,CREATE_TIME,CREATED_BY,VERSION,ACTIVE,EMAIL,FIRST_NAME,LAST_NAME,PASSWORD,ROLE)
VALUES
(
  next value for hibernate_sequence,
  now(),
  -1,
  1,
  1,
  'horky.j@gmail.com',
  'Jan',
  'Horky',
  '9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684',
  'EDITOR'
);

INSERT INTO DISCIPLINE
(
  ID,
  CREATE_TIME,
  CREATED_BY,
  VERSION,
  TITLE
)
VALUES
(
  next value for hibernate_sequence,
  now(),
  1,
  1,
  'Organ transplantation'
);

INSERT INTO JOURNAL
(
  ID,
  CREATE_TIME,
  CREATED_BY,
  VERSION,
  TITLE,
  ABBR_TITLE,
  CODEN,
  FREQUENCY,
  IMPACT_FACTOR,
  IMPACT_FACTOR_YEAR,
  ISSN,
  LANGUAGE,
  LCCN,
  OCLC,
  PUBLIC_FROM,
  PUBLIC_TO,
  PUBLISHER,
  DISCIPLINE,
  EDITED_BY
)
VALUES
(
  next value for hibernate_sequence,
  now(),
  1,
  1,
  'American Journal of Transplantation',
  'Am. J. Transplant.',
  'AJTMBR',
  'MONTHLY',
  5.683,
  2014,
  '1600-6135',
  'English',
  '2001229844',
  '47727849',
  '2001-01-01',
  NULL,
  'Wiley-Blackwell on behalf of the American Society of Transplant Surgeons and the American Society of Transplantation (United States)',
  (select max(id) from DISCIPLINE),
  (select max(id) from USER)
);
