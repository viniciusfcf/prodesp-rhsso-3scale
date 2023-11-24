curl \
  -d "client_id=my-client" \
  -d "client_secret=atJ0UjdA5zzOVcFScpzr4eeqGWGRsdmu" \
  -d "grant_type=client_credentials" \
  -d "groups[0]=admin" \
  -d "groups[1]=guest" \
  -d "owner=Vinicius Ferraz Campos" \
  -d "orgao=Tribunal 1" \
  "http://localhost:8080/realms/my-realm/protocol/openid-connect/token"