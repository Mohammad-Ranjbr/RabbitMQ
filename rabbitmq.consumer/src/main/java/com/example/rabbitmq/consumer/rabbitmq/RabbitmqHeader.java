package com.example.rabbitmq.consumer.rabbitmq;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public class RabbitmqHeader {

    // The RabbitmqHeader code manages information related to the header of RabbitMQ messages, specifically for the Retry and Dead Letter mechanisms.

    // KEYWORD_QUEUE_WAIT → To identify the "wait" queue that is specific to Retry.
    // KEYWORD_QUEUE_WAIT → This constant is used to identify the queues related to Retry (wait queue).
    // Because in the Delayed Retry strategy, failed messages are first sent to a "wait queue" and after some time they are returned to the main queue.
    // xDeaths → List of message death history (Dead Letter History) read from the x-death header.

    // RabbitMQ usually keeps a maximum of two values in x-death (last time message died + first time message died).
    // In ArrayList, if the initial capacity is too small, when adding more data, the capacity of the list doubles, which causes additional processing and memory allocation overhead.
    // It optimizes memory and prevents repeated capacity increases.

    private static final String KEYWORD_QUEUE_WAIT = "wait";
    private List<RabbitmqHeaderXDeath> xDeaths = new ArrayList<>(2);
    private String xFirstDeathExchange = "";
    private String xFirstDeathQueue = "";
    private String xFirstDeathReason = "";

    // In RabbitMQ, every time a message is sent to DLX, information about it is stored in the x-death header. This information includes:
    // reason → reason for the message death (reject, ttl, max-length, etc.)
    // count → number of times the message has been placed in DLX.
    // exchange and queue → message routing.
    // routingKeys → message routing keys.
    // time → time spent in DLX.
    // These values are stored in a RabbitmqHeaderXDeath object and added to the xDeaths list.

    @SuppressWarnings("unchecked")
    public RabbitmqHeader(Map<String, Object> headers) {

        // Why is this information read from headers?
        // In RabbitMQ, when a message is sent to the Dead Letter Exchange (DLX), a series of special headers are added to it:
        // x-first-death-exchange → The first Exchange that sent the message to the DLX.
        // x-first-death-queue → The first Queue in which the message died.
        // x-first-death-reason → The reason the message died the first time (e.g. TTL or Reject).
        // Purpose? Identify the root cause of the problem: If a message fails multiple times, we look for the first reason for its death.

        if (headers != null) {
            var xFirstDeathExchange = Optional.ofNullable(headers.get("x-first-death-exchange"));
            var xFirstDeathQueue = Optional.ofNullable(headers.get("x-first-death-queue"));
            var xFirstDeathReason = Optional.ofNullable(headers.get("x-first-death-reason"));

            xFirstDeathExchange.ifPresent(s -> this.setxFirstDeathExchange(s.toString()));
            xFirstDeathQueue.ifPresent(s -> this.setxFirstDeathQueue(s.toString()));
            xFirstDeathReason.ifPresent(s -> this.setxFirstDeathReason(s.toString()));

            var xDeathHeaders = (List<Map<String, Object>>) headers.get("x-death");

            if (xDeathHeaders != null) {
                for (Map<String, Object> x : xDeathHeaders) {
                    RabbitmqHeaderXDeath hdrDeath = new RabbitmqHeaderXDeath();
                    var reason = Optional.ofNullable(x.get("reason"));
                    var count = Optional.ofNullable(x.get("count"));
                    var exchange = Optional.ofNullable(x.get("exchange"));
                    var queue = Optional.ofNullable(x.get("queue"));
                    var routingKeys = Optional.ofNullable(x.get("routing-keys"));
                    var time = Optional.ofNullable(x.get("time"));

                    reason.ifPresent(s -> hdrDeath.setReason(s.toString()));
                    count.ifPresent(s -> hdrDeath.setCount(Integer.parseInt(s.toString())));
                    exchange.ifPresent(s -> hdrDeath.setExchange(s.toString()));
                    queue.ifPresent(s -> hdrDeath.setQueue(s.toString()));
                    routingKeys.ifPresent(r -> {
                        var listR = (List<String>) r;
                        hdrDeath.setRoutingKeys(listR);
                    });
                    time.ifPresent(d -> hdrDeath.setTime((Date) d));

                    xDeaths.add(hdrDeath);
                }
            }
        }
    }

    public int getFailedRetryCount() {
        // get from queue "wait"
        for (var xDeath : xDeaths) {
            if (xDeath.getExchange().toLowerCase().endsWith(KEYWORD_QUEUE_WAIT)
                    && xDeath.getQueue().toLowerCase().endsWith(KEYWORD_QUEUE_WAIT)) {
                return xDeath.getCount();
            }
        }
        return 0;
    }

    public void setxDeaths(List<RabbitmqHeaderXDeath> xDeaths) {
        this.xDeaths = xDeaths;
    }

    public void setxFirstDeathExchange(String xFirstDeathExchange) {
        this.xFirstDeathExchange = xFirstDeathExchange;
    }

    public void setxFirstDeathQueue(String xFirstDeathQueue) {
        this.xFirstDeathQueue = xFirstDeathQueue;
    }

    public void setxFirstDeathReason(String xFirstDeathReason) {
        this.xFirstDeathReason = xFirstDeathReason;
    }
}
