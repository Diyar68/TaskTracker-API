import { useEffect, useState } from "react";
import TaskForm from "./components/TaskForm";
import TaskList from "./components/TaskList";
import { createTask, getTasks } from "./services/taskService";
import type { Task } from "./types/Task";

function App() {
  const [tasks, setTasks] = useState<Task[]>([]);

  useEffect(() => {
    async function loadTasks() {
      try {
        const data = await getTasks();
        setTasks(data);
      } catch (error) {
        console.error(error);
      }
    }

    void loadTasks();
  }, []);

  async function handleCreateTask(description: string) {
    const createdTask = await createTask(description);

    setTasks((currentTasks) => [...currentTasks, createdTask]);
  }

  return (
    <>
      <h1>TaskTracker</h1>

      <TaskForm onCreateTask={handleCreateTask} />

      <TaskList tasks={tasks} />
    </>
  );
}

export default App;