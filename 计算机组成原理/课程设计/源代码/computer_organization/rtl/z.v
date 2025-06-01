/*标志寄存器*/
module z(din,clk,rst,zload,dout);
input din, clk, rst, zload;
output dout;
reg dout;

always@(posedge clk or negedge rst)
begin 
if(rst==0)
	dout<=0;
else if(zload)
	dout<=din;
end
endmodule