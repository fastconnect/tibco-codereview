package com.mycompany.sonar.reference.batch;

import com.mycompany.sonar.reference.ExampleMetrics;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

public class ExampleSensor implements Sensor {

  public boolean shouldExecuteOnProject(Project project) {
    // This sensor is executed on any type of projects
    return true;
  }

  public void analyse(Project project, SensorContext sensorContext) {
    Measure measure = new Measure(ExampleMetrics.MESSAGE, "Hello World!");
    sensorContext.saveMeasure(measure);
  }
}
