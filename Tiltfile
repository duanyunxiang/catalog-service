# Build
custom_build(
    # Name of the container image
    ref = 'catalog-service',
    # On Windows, replace $EXPECTED_REF with %EXPECTED_REF%
    # ./gradlew会报错，去掉./
    command = 'gradlew bootBuildImage --imageName %EXPECTED_REF%',
    # Files to watch that trigger a new build
    deps = ['build.gradle', 'src']
)

# Deploy
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

# Manage
k8s_resource('catalog-service', port_forwards=['9001'])