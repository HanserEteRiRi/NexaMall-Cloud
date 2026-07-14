# Nacos configuration notice

The previous `nacos.sql` database dump was removed because it mixed Nacos schema data with environment-specific endpoints and credentials.

Use the schema bundled with the selected Nacos distribution. Application configuration must be created from sanitized templates and secrets must be supplied through environment variables or a secret manager. Never commit a Nacos database dump containing `config_info` rows.

Credentials that appeared in Git history must be rotated at their providers; deleting the current file does not remove historical Git objects.
