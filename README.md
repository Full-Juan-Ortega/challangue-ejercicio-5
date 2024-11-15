# Caso 5 - Desarrollo de pipeline para el buildeo y despliegue de servicio en el Cluster
Necesitamos un pipeline de Jenkins que buildee y suba a un registry la imagen de docker anteriormente abordada(Caso 1). Luego del buildeo, necesitamos que el servicio se despliegue en el minikube(Caso 3).  
[Repo Imagen caso 1](https://github.com/Gonveliz/node-app/blob/master/Dockerfile)

## Como desplegar
## Analisis resumido y depurado
## Proceso

### Mejoras : 
- Warning credenciales de docker.  
Revise la doc oficial y encontre mejores practicas ( aunque me sigue tirando el warning )
<https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#handling-credentials>

-