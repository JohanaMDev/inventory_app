import { Link } from "react-router-dom";

const Sidebar = () => {
  const menuItems = [
    { name: "Dashboard", icon: "📊", path: "/dashboard" },
    { name: "Productos", icon: "📦", path: "/products" },
    { name: "Proveedores", icon: "🤝", path: "/suppliers" },
    { name: "Ventas", icon: "💰", path: "/sales" },
  ];

  return (
    <aside className="w-64 bg-slate-900 border-r border-white/5 min-h-screen p-6">
      <div className="mb-10">
        <h2 className="text-xl font-bold bg-gradient-to-r from-blue-400 to-purple-500 bg-clip-text text-transparent">
          GLOW & GO
        </h2>
      </div>

      <nav className="space-y-2">
        {menuItems.map((item) => (
          <Link
            key={item.name}
            to={item.path}
            className="flex items-center space-x-3 text-slate-400 hover:text-white hover:bg-white/5 p-3 rounded-xl transition-all group"
          >
            <span className="text-lg group-hover:scale-110 transition-transform">
              {item.icon}
            </span>
            <span className="font-medium">{item.name}</span>
          </Link>
        ))}
      </nav>
    </aside>
  );
};

export default Sidebar;
