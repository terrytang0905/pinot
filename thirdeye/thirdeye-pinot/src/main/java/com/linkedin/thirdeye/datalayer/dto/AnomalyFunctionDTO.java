package com.linkedin.thirdeye.datalayer.dto;

import com.linkedin.thirdeye.datalayer.pojo.AnomalyFunctionBean;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnomalyFunctionDTO extends AnomalyFunctionBean {
  private static final Logger LOGGER = LoggerFactory.getLogger(AnomalyFunctionDTO.class);

  public String getTopicMetric() {
    return getMetric();
  }

  public void setTopicMetric(String topicMetric) {
    setMetric(topicMetric);
  }

  // TODO: Remove this method and update the value in DB instead
  /**
   * Returns the list of metrics to be retrieved for anomaly detection. If the information is not
   * provided in the DB, then it returns a list that contains only topic metric.
   *
   * @return a list of metrics to be retrieved for anomaly detection.
   */
  @Override
  public List<String> getMetrics() {
    if (CollectionUtils.isEmpty((super.getMetrics()))) {
      return Arrays.asList(getMetric());
    } else {
      return super.getMetrics();
    }
  }

  /**
   * Parses the properties of String and returns the corresponding Properties object.
   *
   * @return a Properties object corresponds to the properties String of this anomaly function.
   */
  public Properties toProperties() {
    Properties props = new Properties();

    if (this.getProperties() != null) {
      String[] tokens = getProperties().split(";");
      for (String token : tokens) {
        try {
          props.load(new ByteArrayInputStream(token.getBytes()));
        } catch (IOException e) {
          LOGGER.warn("Failed to parse property string ({}) for anomaly function: {}", token, getId());
        }
      }
    }
    return props;
  }
}
