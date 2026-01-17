package dev.neovoxel.neobot.adapter;
import com.hypixel.hytale.server.core.task.TaskRegistration;
import dev.neovoxel.neobot.scheduler.ScheduledTask;
import org.graalvm.polyglot.HostAccess;

import java.util.HashSet;
import java.util.Set;

public class HytaleSchedulerTask implements ScheduledTask {
    private TaskRegistration taskRegistration;
    private static final Set<TaskRegistration> tasks = new HashSet<>();

    public HytaleSchedulerTask(TaskRegistration task) {
        this.taskRegistration = task;
        tasks.add(task);
    }

    @HostAccess.Export
    @Override
    public void cancel() {
        taskRegistration.unregister();
    }

    public static void cancelAllTasks() {
        tasks.forEach(TaskRegistration::unregister);
        tasks.clear();
    }
}
