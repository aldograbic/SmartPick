FROM eclipse-temurin:21-jdk

VOLUME /tmp

RUN mkdir -p /app/resources/static/python

RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    ln -s /usr/bin/python3 /usr/bin/python

COPY requirements.txt /app/requirements.txt

RUN pip install -r /app/requirements.txt

COPY target/*.jar app.jar

COPY src/main/resources/static/python/recommendation_script_content_based.py /app/resources/static/python/recommendation_script_content_based.py

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]