// src/components/Sidebar.jsx
import { Listbox, ListboxItem, Button } from "@nextui-org/react";
import { FaUser, FaSignOutAlt } from "react-icons/fa";

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div>
        <h2 className="text-xl font-bold text-white mb-6 text-center">
          Employee MS
        </h2>

        <Listbox aria-label="Sidebar menu" variant="flat" className="gap-2 text-white">
          <ListboxItem
            key="employees"
            startContent={<FaUser />}
            className="bg-teal-600 rounded text-white font-bold"
          >
            Employees
          </ListboxItem>
        </Listbox>
      </div>

      <div className="text-center mt-6">
        <Button
          color="danger"
          variant="flat"
          size="sm"
          startContent={<FaSignOutAlt />}
        >
          Logout
        </Button>
      </div>
    </aside>
  );
}
