ARG CI_REGISTRY
ARG IMAGE=gradle:7.4.2-jdk17

FROM ${IMAGE}

ADD build/libs/rebalance.jar .
ENTRYPOINT exec java -Dspring.profiles.active=$PROFILE $JAVA_MEM $JAVA_EXT -jar rebalance.jar