package com.redhat.keycloak;
import java.util.ArrayList;
import java.util.List;

import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;
import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.AccessToken;

import jakarta.ws.rs.core.MultivaluedMap;


public class CustomProtocolMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper,
  OIDCIDTokenMapper, UserInfoTokenMapper {

    public static final String PROVIDER_ID = "vinicius-protocol-mapper";

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

    static {
        OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
        OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, CustomProtocolMapper.class);
    }

    @Override
    public String getDisplayCategory() {
        return "Token mapper";
    }

    @Override
    public String getDisplayType() {
        return "Custom Token Mapper Vinicius";
    }

    @Override
    public String getHelpText() {
        return "Adds a Vinicius text to the claim";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    // @Override
    // protected void setClaim(IDToken token, ProtocolMapperModel mappingModel,
    //   UserSessionModel userSession, KeycloakSession keycloakSession,
    //   ClientSessionContext clientSessionCtx) {
    //     OIDCAttributeMapperHelper.mapClaim(token, mappingModel, "Vinicius");
    //     token.getOtherClaims().put("tempo", LocalDateTime.now());
    // }

    @Override
    public AccessToken transformAccessToken(AccessToken token, ProtocolMapperModel mappingModel,
            KeycloakSession session, UserSessionModel userSession, ClientSessionContext clientSessionCtx) {
        // System.out.println("#### RequestHeaders=" + session.getContext().getHttpRequest().getHttpHeaders().getRequestHeaders());
        // System.out.println("#### DecodedFormParameters=" + session.getContext().getHttpRequest().getDecodedFormParameters());
        MultivaluedMap<String, String> parameters = session.getContext().getHttpRequest().getDecodedFormParameters();
        token.getOtherClaims().put("owner", parameters.get("owner"));
        token.getOtherClaims().put("groups", new ArrayList<>());
        parameters.keySet().stream().filter(p -> p.startsWith("groups")).forEach(k -> {
            List<String> groups = (List<String>) token.getOtherClaims().get("groups");
            groups.add(parameters.get(k).get(0));
        });
        
        //Mostrando que conseguimos sobrescrever um claim default
        token.getOtherClaims().put("azp", "cliente-sobrescrito");
        
        return token;
    }
}