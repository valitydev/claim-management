--remove payout
DROP TABLE IF EXISTS cm.shop_payout_tool_modification_model;
DROP TABLE IF EXISTS cm.shop_payout_schedule_modification_model;
DROP TABLE IF EXISTS cm.contract_payout_tool_creation_modification_model;
DROP TABLE IF EXISTS cm.contract_payout_tool_change_modification_model;
DROP TABLE IF EXISTS cm.contract_payout_tool_modification_model;
ALTER TABLE cm.shop_params_model DROP COLUMN payout_tool_id;
ALTER TABLE cm.shop_contract_modification_model DROP COLUMN payout_tool_id;
DROP TABLE IF EXISTS cm.payout_tool_info_model;
DROP TABLE IF EXISTS cm.payout_tool_params_model;

--- add contact_info fields
ALTER TABLE cm.contact_info_model ADD COLUMN first_name character varying;
ALTER TABLE cm.contact_info_model ADD COLUMN last_name character varying;
ALTER TABLE cm.contact_info_model ADD COLUMN country character varying;
ALTER TABLE cm.contact_info_model ADD COLUMN state character varying;
ALTER TABLE cm.contact_info_model ADD COLUMN city character varying;
ALTER TABLE cm.contact_info_model ADD COLUMN address character varying;
ALTER TABLE cm.contact_info_model ADD COLUMN postal_code character varying;


