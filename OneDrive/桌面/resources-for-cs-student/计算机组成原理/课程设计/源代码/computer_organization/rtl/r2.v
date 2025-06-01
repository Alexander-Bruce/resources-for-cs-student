/*r2通用寄存器*/
module r2(din, clk, rst,r2load, dout);
input[7:0] din;
input clk, rst, r2load;
output[7:0] dout;
reg[7:0] dout;

always@(posedge clk or negedge rst)
begin 
if(rst==0)
	dout<=0;
else if(r2load)
	dout<=din;
end
endmodule