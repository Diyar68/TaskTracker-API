import { useEffect, useState } from "react";
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

      {tasks.map((task) => (
        <div key={task.id}>
          <h3>{task.description}</h3>

          <p>Status: {task.status}</p>

          <small>
            Created: {task.createdAt}
          </small>
        </div>
      ))}
    </>
  );
}

export default App;