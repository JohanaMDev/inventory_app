import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";
import InputField from "../components/InputField";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        {
          email,
          password,
        },
      );

      console.log("Respuesta del servidor:", response.data);

      // Ajustamos para capturar el token sin importar cómo lo llame el backend
      const token = response.data.acces_token;

      if (token) {
        localStorage.setItem("token", token);
        console.log("¡Token guardado correctamente en Application!");
        navigate("/dashboard");
      } else {
        console.error(
          "No se encontró la propiedad 'acces_token' en la respuesta.",
        );
      }
    } catch (err) {
      console.error("Error en el login:", err);
    }
  };

  return (
    <div className="min-h-screen bg-slate-950 flex items-center justify-center p-4">
      <div className="bg-slate-900 border border-slate-800 p-8 rounded-2xl shadow-xl w-full max-w-md text-center">
        <h2 className="text-3xl font-bold text-white mb-2">Bienvenidos</h2>
        <p className="text-slate-400 mb-8">Ingresá al Sistema de Inventario</p>

        <form onSubmit={handleLogin} className="space-y-6 text-left">
          <InputField
            label="Email"
            type="email"
            placeholder="admin@mendoza.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <InputField
            label="Password"
            type="password"
            placeholder="••••••••"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          {error && (
            <p className="text-red-400 text-sm text-center font-medium">
              {error}
            </p>
          )}

          <Button type="submit" variant="primary">
            Iniciar Sesión
          </Button>
        </form>
      </div>
    </div>
  );
}

export default Login;
