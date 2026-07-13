import "./TaskCard.css";
import type { Task } from "../types/Task";

interface TaskCardProps {
  task: Task;
}

function TaskCard({ task }: TaskCardProps) {
  return (
    <div className="task-card">
      <h3>{task.description}</h3>

      <p className="task-status">
        Status: {task.status}
      </p>

      <p className="task-date">
        Created: {task.createdAt}
      </p>
    </div>
  );
}

export default TaskCard;