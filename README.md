# prodesp-rhsso-3scale

## Configuração
- Download do keycloak 22
- Build do mapper `mvn clean package` na pasta `keycloak-custom-mapper`
- Copiar jar para o Keycloak `cp keycloak-custom-mapper/target/keycloak-custom-mapper-0.0.1-SNAPSHOT.jar <KC_HOME>/providers`
- Iniciar Keycloak `<KC_HOME>/bin/kc.sh start-dev`
- Na Tela inicial do keycloak, na aba `Provider info` verificar que o existe o mapper `vinicius-protocol-mapper`
- Criar o realm `my-realm`
- Criar o client `my-client` com `Client authentication=on` e `Service accounts roles=on`
- Criar client scope `my-client-scope`
- Configurar no client `my-client` na aba `client scope`. Adicionar o Mapper by configuration

## Teste

- Atualizar arquivo `create-token.sh` com a secret do client `my-client`
- executar `./create-token.sh`