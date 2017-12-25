
Dev profile:

    mvn clean test

Staging|Prod profile:

    docker run -d --name postgres postgres
    docker run --rm -it -v $PWD:/src -w /src --link postgres walkingdevs/mvn mvn clean test -Pstaging|prod
    docker rm -f postgres


Dev profile:

    mvn clean package tomee:run -DskipTests

Staging|Prod profile:

    docker run -d --name postgres postgres
    docker run --rm -it -v $PWD:/src -w /src --link postgres walkingdevs/mvn mvn clean package tomee:run -DskipTests -Pstaging|prod
    docker rm -f postgres


Dev profile:

    mvn clean package tomee:exec -DskipTests
    docker build --rm -t app .

Staging|Prod profile:

    mvn clean package tomee:exec -DskipTests -Pstaging|prod
    docker build --rm -t app .

    
    Root     /
    Binding  0.0.0.0
    Port     8080
    
    http://localhost:8080


    mvn clean package tomee:run -Prebel

CLI:

    # Awesome changes...
    mvn compile

IDEA:
    
    # Awesome changes...
    Build->Make project

Look at console, see message?, done.