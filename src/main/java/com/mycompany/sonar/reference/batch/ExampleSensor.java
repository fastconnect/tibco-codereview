package com.mycompany.sonar.reference.batch;

import com.mycompany.sonar.reference.ExampleMetrics;
import com.mycompany.sonar.reference.ExamplePlugin;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

public class ExampleSensor implements Sensor {

  private Settings settings;

  /**
   * Use of IoC to get Settings
   */
  public ExampleSensor(Settings settings) {
    this.settings = settings;
  }

  @Override
  public boolean shouldExecuteOnProject(Project project) {
    // This sensor is executed on any type of projects
    return true;
  }

  @Override
  public void analyse(Project project, SensorContext sensorContext) {
    Measure measure = new Measure(ExampleMetrics.MESSAGE, settings.getString(ExamplePlugin.MY_PROPERTY));
    sensorContext.saveMeasure(measure);
  }
}
