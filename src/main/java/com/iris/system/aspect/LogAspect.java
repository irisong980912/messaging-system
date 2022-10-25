package com.iris.system.aspect;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Log4j2
public class LogAspect {

    private static final String METRIC_NAMESPACE = "MessagingService";
    @Autowired
    private AmazonCloudWatch amazonCloudWatch;

    @Around("execution(* com.iris.system.controller.*.*(..))") // joinpoint
    public Object logRequestHandling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        boolean error = false;
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            error = true;
            throw exception;
        } finally {
            String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
            String methodName = proceedingJoinPoint.getSignature().getName();
            long endTime = System.currentTimeMillis();
            double latency = (double) endTime - startTime;

            log.info("{}.{} latency: {} ms, error: {}", className, methodName, endTime - startTime, error);


            MetricDatum latencyMetric = new MetricDatum()
                    .withUnit(StandardUnit.Milliseconds)
                    .withValue(latency)
                    .withMetricName("Time")
                    .withTimestamp(new Date())
                    .withDimensions(new Dimension().withName("Controller").withValue(className),
                            new Dimension().withName("Method").withValue(methodName));

            // Controller
            PutMetricDataRequest putMetricDataRequest = new PutMetricDataRequest()
                    .withNamespace(METRIC_NAMESPACE)
                    .withMetricData(latencyMetric);
            this.amazonCloudWatch.putMetricData(putMetricDataRequest);
        }

    }
}
