import Sidebar from "../layout/Sidebar";
import Button from "../components/Button";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div className="flex min-h-screen bg-slate-950 text-white">
      {/* 1. Menú Lateral Fijo */}
      <Sidebar />

      {/* 2. Contenedor de Contenido Principal */}
      <main className="flex-1 p-8">
        <header className="flex justify-between items-center mb-10">
          <div>
            <h1 className="text-3xl font-bold tracking-tight text-white">
              Panel de Control
            </h1>
            <p className="text-slate-400 mt-1">
              Bienvenida de nuevo al sistema de Glow & Go.
            </p>
          </div>

          <div className="w-32">
            <Button
              onClick={handleLogout}
              variant="danger"
              className="py-2 text-sm"
            >
              Cerrar Sesión
            </Button>
          </div>
        </header>

        {/* 3. Grid de Estadísticas Rápidas */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <StatCard
            title="Total Productos"
            value="124"
            icon="📦"
            color="text-blue-400"
          />
          <StatCard
            title="Proveedores"
            value="12"
            icon="🤝"
            color="text-purple-400"
          />
          <StatCard
            title="Ventas del Mes"
            value="$45.200"
            icon="💰"
            color="text-emerald-400"
          />
        </div>

        {/* Aquí es donde más adelante renderizaremos las tablas de proveedores o productos */}
      </main>
    </div>
  );
}

// Un pequeño sub-componente interno para no repetir código de las tarjetas
const StatCard = ({ title, value, icon, color }) => (
  <div className="bg-slate-900 border border-white/5 p-6 rounded-2xl hover:border-white/10 transition-colors">
    <div className="flex items-center justify-between mb-4">
      <span className="text-2xl">{icon}</span>
      <span className={`text-xs font-bold uppercase tracking-wider ${color}`}>
        En tiempo real
      </span>
    </div>
    <h3 className="text-slate-400 text-sm font-medium">{title}</h3>
    <p className="text-2xl font-bold mt-1">{value}</p>
  </div>
);

export default Dashboard;
