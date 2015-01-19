package com.mycompany.sonar.reference.batch;

import com.mycompany.sonar.reference.ExampleMetrics;
import org.apache.commons.lang.math.RandomUtils;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;

public class RandomDecorator implements Decorator {

  public boolean shouldExecuteOnProject(Project project) {
    return true;
  }

  public void decorate(Resource resource, DecoratorContext context) {
    // This method is executed on the whole tree of resources.
    // Bottom-up navigation : Files -> Dirs -> modules -> project
    if (ResourceUtils.isFile(resource)) {
      // Sonar API includes many libraries like commons-lang and google-collections
      double value = RandomUtils.nextDouble();

      // Add a measure to the current File
      context.saveMeasure(ExampleMetrics.RANDOM, value);

    } else {
      // we sum random values on resources different than file. Another option would be to declare a Formula on metric declaration
      context.saveMeasure(ExampleMetrics.RANDOM, MeasureUtils.sum(true, context.getChildrenMeasures(ExampleMetrics.RANDOM)));
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
