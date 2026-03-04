import { Link } from "react-router-dom";

function Home() {
  return (
    <div
      style={{ backgroundColor: "black", minHeight: "100vh", color: "white" }}
    >
      {/* Navbar Simple */}
      <nav className="flex justify-between items-center p-6 max-w-7xl mx-auto border-b border-white/5">
        <h1 className="text-2xl font-bold tracking-tighter bg-gradient-to-r from-blue-400 to-purple-500 bg-clip-text text-transparent">
          GLOW & GO{" "}
          <span className="text-sm font-light text-slate-500">Mendoza</span>
        </h1>
        <div className="space-x-8 text-sm font-medium text-slate-400">
          <a href="#catalogo" className="hover:text-blue-400 transition-colors">
            Catálogo
          </a>
          <Link
            to="/login"
            className="bg-white/5 hover:bg-white/10 px-4 py-2 rounded-full border border-white/10 transition-all"
          >
            Acceso Personal
          </Link>
        </div>
      </nav>

      {/* Hero Section */}
      <main className="max-w-7xl mx-auto px-6 py-24 text-center">
        <div className="inline-block px-3 py-1 rounded-full bg-blue-500/10 border border-blue-500/20 text-blue-400 text-xs font-bold mb-6 tracking-widest uppercase">
          Gestión de Inventario v1.0
        </div>
        <h2 className="text-5xl md:text-7xl font-extrabold mb-8 tracking-tight">
          Control total para tu <br />
          <span className="text-blue-500">negocio.</span>
        </h2>
        <p className="text-slate-400 max-w-2xl mx-auto text-lg mb-12">
          Sistema integral para la gestión de stock, proveedores y ventas.
          Optimizado para el alto rendimiento y decisiones basadas en datos.
        </p>

        <div className="flex flex-col md:flex-row justify-center gap-4">
          <button className="bg-blue-600 hover:bg-blue-500 text-white px-8 py-4 rounded-xl font-bold transition-all shadow-lg shadow-blue-500/25">
            Explorar Catálogo
          </button>
          <button className="bg-slate-800 hover:bg-slate-700 text-white px-8 py-4 rounded-xl font-bold transition-all border border-slate-700">
            Registrarse como Cliente
          </button>
        </div>
      </main>

      {/* Grid de Características (Placeholder) */}
      <section
        id="catalogo"
        className="max-w-7xl mx-auto px-6 py-20 border-t border-white/5"
      >
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {[1, 2, 3].map((item) => (
            <div
              key={item}
              className="bg-slate-900/50 border border-white/5 p-8 rounded-2xl hover:border-blue-500/50 transition-colors group"
            >
              <div className="w-12 h-12 bg-blue-500/20 rounded-lg mb-6 flex items-center justify-center group-hover:scale-110 transition-transform">
                <div className="w-6 h-6 bg-blue-500 rounded-sm"></div>
              </div>
              <h3 className="text-xl font-bold mb-3">Línea de Maquillaje</h3>
              <p className="text-slate-400 text-sm leading-relaxed">
                Visualización de productos en tiempo real con integración
                directa a nuestro almacén central.
              </p>
            </div>
          ))}
        </div>
      </section>

      <footer className="py-10 text-center text-slate-600 text-sm border-t border-white/5">
        &copy; 2026 Glow & Go Mendoza - Desarrollado con Java & React
      </footer>
    </div>
  );
}

export default Home;
