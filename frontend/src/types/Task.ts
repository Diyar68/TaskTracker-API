export type TaskStatus = "OPEN" | "IN_PROGRESS" | "DONE";

export interface Task {
    id: number;
    description: string;
    status: TaskStatus;
    createdAt: string;
    updatedAt: string;
}