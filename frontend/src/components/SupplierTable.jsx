const SupplierTable = ({ suppliers }) => {
  return (
    <div className="bg-slate-900 border border-white/5 rounded-2xl overflow-hidden mt-8">
      <table className="w-full text-left border-collapse">
        <thead className="bg-white/5 text-slate-400 text-xs uppercase tracking-widest">
          <tr>
            <th className="p-4 font-semibold">Nombre</th>
            <th className="p-4 font-semibold">Contacto</th>
            <th className="p-4 font-semibold">Categoría</th>
            <th className="p-4 font-semibold text-right">Acciones</th>
          </tr>
        </thead>
        <tbody className="text-sm">
          {suppliers.length > 0 ? (
            suppliers.map((s) => (
              <tr
                key={s.id}
                className="border-t border-white/5 hover:bg-white/[0.02] transition-colors"
              >
                <td className="p-4 font-medium text-white">{s.name}</td>
                <td className="p-4 text-slate-400">{s.email || s.phone}</td>
                <td className="p-4">
                  <span className="px-2 py-1 bg-blue-500/10 text-blue-400 rounded-md text-xs border border-blue-500/20">
                    {s.category || "General"}
                  </span>
                </td>
                <td className="p-4 text-right space-x-3">
                  <button className="text-slate-500 hover:text-white transition-colors">
                    Editar
                  </button>
                  <button className="text-red-500/50 hover:text-red-500 transition-colors">
                    Eliminar
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td
                colSpan="4"
                className="p-10 text-center text-slate-500 italic"
              >
                No hay proveedores registrados aún.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default SupplierTable;
