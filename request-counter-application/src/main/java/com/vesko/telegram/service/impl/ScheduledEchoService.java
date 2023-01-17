package com.vesko.telegram.service.impl;

import com.vesko.dynamicconfiguration.service.ConfigurationService;
import com.vesko.telegram.dto.EchoMessageRequest;
import com.vesko.telegram.service.EchoMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledEchoService {
    private final ConcurrentMap<String, PriorityBlockingQueue<DelayedEchoMessage>> keyedDelayedContainers = new ConcurrentHashMap<>();
    private final ConfigurationService configurationService;
    private final EchoMessageService echoMessageService;

    /**
     * @param echoMessageRequest полученное через REST API сообщение, содержащее текст и имя целевого пользователя
     */
    public void schedule(EchoMessageRequest echoMessageRequest) {
        var wakeUpTime = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(configurationService.getDelay());
        var scheduledMessage = new DelayedEchoMessage(echoMessageRequest, wakeUpTime);
        var keyedQueue = keyedDelayedContainers.compute(echoMessageRequest.getTargetUsername(), (k, v) -> {
            if (v == null) {
                v = new PriorityBlockingQueue<>();
            }
            return v;
        });

        keyedQueue.add(scheduledMessage);
    }

    /**
     * Таймер для поиска сообщений готовых для отправки.
     */
    @Scheduled(fixedRate = 50)
    void watchForReadyTasks() {
        var current = System.nanoTime();

        keyedDelayedContainers.forEach((key, taskQueue) -> {
            DelayedEchoMessage delayedEchoMessage;
            while ((delayedEchoMessage = taskQueue.peek()) != null) {
                if (delayedEchoMessage.wakeUpTime <= current) {
                    sendDelayedMessage(taskQueue.poll());
                    log.info("Processed message {} from {}",
                            delayedEchoMessage.body.getMessage(),
                            delayedEchoMessage.body.getTargetUsername()
                    );
                } else {
                    break;
                }
            }
        });
    }

    @Async
    void sendDelayedMessage(DelayedEchoMessage task) {
        try {
            echoMessageService.echoMessageRequest(task.body)
                    .execute();
        } catch (IOException e) {
            log.info("Message can't be processed", e);
        }
    }

    private record DelayedEchoMessage(EchoMessageRequest body,
                                      long wakeUpTime) implements Comparable<DelayedEchoMessage> {
        @Override
        public int compareTo(DelayedEchoMessage delayedEchoMessage) {
            return Long.compare(wakeUpTime, delayedEchoMessage.wakeUpTime);
        }
    }
}
