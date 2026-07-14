import { useState } from "react";
import "./TaskForm.css";

interface TaskFormProps {
  onCreateTask: (description: string) => Promise<void>;
}

function TaskForm({ onCreateTask }: TaskFormProps) {
  const [description, setDescription] = useState("");

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const trimmedDescription = description.trim();

    if (trimmedDescription === "") {
      return;
    }

    try {
      await onCreateTask(trimmedDescription);
      setDescription("");
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <form className="task-form" onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Enter a new task"
        value={description}
        onChange={(event) => setDescription(event.target.value)}
      />

      <button type="submit">Add Task</button>
    </form>
  );
}

export default TaskForm;