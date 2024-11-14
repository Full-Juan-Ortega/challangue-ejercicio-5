# Imagen basada en jenkins + terraform + aws cli
FROM jenkins/jenkins:lts

USER root

# Actualizar y preparar el sistema para la instalación de Terraform
RUN apt update && \
    apt install -y wget gnupg lsb-release software-properties-common && \
    wget -O- https://apt.releases.hashicorp.com/gpg | gpg --dearmor -o /usr/share/keyrings/hashicorp-archive-keyring.gpg --yes && \
    echo "deb [signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] https://apt.releases.hashicorp.com $(lsb_release -cs) main" | tee /etc/apt/sources.list.d/hashicorp.list && \
    apt update && \
    apt install -y terraform

# Instalar AWS CLI
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" && \
    unzip awscliv2.zip && \
    ./aws/install && \
    rm -rf awscliv2.zip aws

# Instalar docker para usar el cliente y el daemon del host.

RUN apt install -y docker.io

# Instalar kubectl para interactuar con la api del host (instalacion oficial)"

RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" && \

curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl.sha256" && \

install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl







