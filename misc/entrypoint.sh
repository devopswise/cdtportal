#!/bin/bash

NEW_CERTS=$(cp /certs/* /usr/local/share/ca-certificates/ -nv | awk '{print $3}' | tr -d \')

update-ca-certificates && \
    echo ${NEW_CERTS} | while read cert; do \
        [ -f "$cert" ] || continue
        openssl x509 -outform der -in $cert -out $cert.der; \
        keytool -import -alias $cert -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -trustcacerts -file $cert.der -storepass changeit -noprompt; \
        rm $cert.der; \
    done

exec "$@"
