/*算术逻辑单元，x来自于Rs，bus来自于Rd*/
module alu(alus,x, bus, dout, zout);
input[2:0] alus;
input[7:0] bus,x;
output[7:0] dout;
output zout;
reg[7:0] dout;
reg zout;

always@(*)
begin
case(alus[2:0])
	3'b000:	dout=bus + x;
	3'b001:	dout=bus - x;
	3'b010:	dout=bus & x;
	3'b011:	dout=bus | x;
	3'b100:	dout=bus + 8'b00000001;
	3'b101:	dout=bus - 8'b00000001;
	3'b110:	dout=~bus;
	3'b111:	dout=bus<<1;
	default:	dout=8'bzzzzzzzz;
endcase
if(dout===8'b00000000)
	zout=1'b1;
else
	zout=1'b0;
end
endmodule
