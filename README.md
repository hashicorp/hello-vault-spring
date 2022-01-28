# hello-vault-spring

This is a sample application that demonstrates how to authenticate to and retrieve secrets from HashiCorp [Vault][vault]
.

## Prerequisites

1. [`docker`][docker] to easily run the application in the same environment regardless of your local operating system
1. [`docker compose`][docker-compose] to easily set up all the components of the demo (the application's web server, the
   Vault server, the database, etc.) all at once
1. [`curl`][curl] to test our endpoints
1. [`jq`][jq] _(optional)_ for prettier `JSON` output

## Try it out

> **WARNING**: The Vault server used in this setup is configured to run in
> `-dev` mode, an insecure setting that allows for easy testing.

### 1. Bring up the services

This step may take a few minutes to download the necessary dependencies.

```shell-session
./run.sh
```

```
[+] Running 8/8
 ⠿ Network hello-vault-spring_default                       Created             0.0s
 ⠿ Volume "hello-vault-spring_trusted-orchestrator-volume"  Created             0.0s
 ⠿ Container hello-vault-spring-secure-service-1            Started             0.7s
 ⠿ Container hello-vault-spring-database-1                  Started             0.7s
 ⠿ Container hello-vault-spring-vault-server-1              Started             2.5s
 ⠿ Container hello-vault-spring-trusted-orchestrator-1      Started             9.7s
 ⠿ Container hello-vault-spring-app-1                       Started             11.5s
 ⠿ Container hello-vault-spring-healthy-1                   Started             13.2s                                                                             0.0s

```

Verify that the services started successfully:

```shell-session
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
```

```
NAMES                                   STATUS                        PORTS
hello-vault-spring-app-1                    Up About a minute (healthy)   0.0.0.0:8080->8080/tcp
hello-vault-spring-trusted-orchestrator-1   Up About a minute (healthy)
hello-vault-spring-vault-server-1           Up About a minute (healthy)   0.0.0.0:8200->8200/tcp
hello-vault-spring-secure-service-1         Up About a minute (healthy)   0.0.0.0:1717->80/tcp
hello-vault-spring-database-1               Up About a minute (healthy)   0.0.0.0:5432->5432/tcp
```

### 2. Try out `POST /payments` endpoint (static secrets workflow)

`POST /payments` endpoint is a simple example of the static secrets workflow. Our service will make a request to another
service's restricted API endpoint using an API key value stored in Vault's static secrets engine.

```shell-session
curl -s -X POST http://localhost:8080/payments | jq
```

```json
{
  "message": "hello world!"
}
```

Check the logs:

```shell-session
docker logs hello-vault-spring-app-1
```

```log
// TODO - add logs from java application
```

### 3. Try out `GET /products` endpoint (dynamic secrets workflow)

`GET /products` endpoint is a simple example of the dynamic secrets workflow. Our application uses Vault's database
secrets engine to generate dynamic database credentials, which are then used to connect to and retrieve data from a
PostgreSQL database.

```shell-session
curl -s -X GET http://localhost:8080/products | jq
```

```json
[
  {
    "id": 1,
    "name": "Rustic Webcam"
  },
  {
    "id": 2,
    "name": "Haunted Coloring Book"
  }
]
```

Check the logs:

```shell-session
docker logs hello-vault-spring-app-1
```

```log
// TODO - add logs from java application
```

### 4. Examine the logs for renew logic

One of the complexities of dealing with short-lived secrets is that they must be renewed periodically. This includes
authentication tokens and database credentials.

Examine the logs for how the Vault auth token is periodically renewed:

```shell-session
docker logs hello-vault-spring-app-1 2>&1 | grep auth
```

```log
// TODO - add logs from java application
```

Examine the logs for database credentials renew / reconnect cycle:

```shell-session
docker logs hello-vault-spring-app-1 2>&1 | grep database
```

```log
// TODO - add logs from java application
```

## Integration Tests

The following script will bring up the docker-compose environment, run the curl commands above, verify the output, and
bring down the environment:

```shell-session
./run-tests.sh
```

## Stack Design

### API

| Endpoint             | Description                                                            |
| -------------------- | ---------------------------------------------------------------------- |
| **POST** `/payments` | A simple example of Vault static secrets workflow (see example above)  |
| **GET** `/products`  | A simple example of Vault dynamic secrets workflow (see example above) |

### Docker Compose Architecture

![arch overview](images/arch-overview.svg)

[vault]:           https://www.vaultproject.io/

[docker]:          https://docs.docker.com/get-docker/

[docker-compose]:  https://docs.docker.com/compose/install/

[curl]:            https://curl.se/

[jq]:              https://stedolan.github.io/jq/
