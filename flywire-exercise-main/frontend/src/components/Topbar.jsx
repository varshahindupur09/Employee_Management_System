// src/components/Topbar.jsx
import { Button } from "@nextui-org/react";

export default function Topbar() {
  return (
    <header className="topbar">
      <h1 className="text-xl font-semibold">Welcome, Admin</h1>
      <Button className="custom-button btn-lg">Logout</Button>
    </header>
  );
}
