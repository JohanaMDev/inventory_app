const Button = ({
  children,
  onClick,
  type = "button",
  variant = "primary",
  className = "",
}) => {
  const baseStyles =
    "w-full font-bold py-3 rounded-lg shadow-lg transition-all transform active:scale-95";

  const variants = {
    primary: "bg-blue-600 hover:bg-blue-500 text-white shadow-blue-500/20",
    secondary:
      "bg-slate-800 hover:bg-slate-700 text-white border border-slate-700",
    danger:
      "bg-red-600/10 hover:bg-red-600/20 text-red-500 border border-red-500/20",
  };

  return (
    <button
      type={type}
      onClick={onClick}
      className={`${baseStyles} ${variants[variant]} ${className}`}
    >
      {children}
    </button>
  );
};

export default Button;
