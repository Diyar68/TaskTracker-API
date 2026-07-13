import { useEffect, useState } from "react";
import TaskList from "./components/TaskList";
import { getTasks } from "./services/taskService";
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

  return (
    <>
      <h1>TaskTracker</h1>

      <TaskList tasks={tasks} />
    </>
  );
}

export default App;